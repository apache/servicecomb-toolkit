/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.toolkit.generator.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import org.apache.servicecomb.toolkit.generator.annotation.ModelInterceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverterContextImpl;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.core.util.RefUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;

public class ModelConverter {

  private static final ModelConverterContextImpl context;

  private static final List<ModelInterceptor> interceptorMgr = new ArrayList<>();

  static {

    ServiceLoader.load(ModelInterceptor.class).forEach(ModelConverter::registerInterceptor);

    ArrayModelConverter arrayModelConverter = new ArrayModelConverter(mapper());
    ModelResolver modelResolver = new ModelResolver(mapper());

    context = new ModelConverterContextImpl(Arrays.asList(arrayModelConverter, modelResolver));
  }

  public static void registerInterceptor(ModelInterceptor interceptor) {
    interceptorMgr.add(interceptor);
    interceptorMgr.sort(Comparator.comparingInt(ModelInterceptor::order));
  }

  public static void unRegisterInterceptor(ModelInterceptor interceptor) {
    interceptorMgr.remove(interceptor);
  }

  public static Schema getSchema(Type cls) {
    return getSchema(cls, null, null);
  }

  public static Schema getSchema(Type cls, Components components, RequestResponse requestResponse) {

    for (ModelInterceptor interceptor : interceptorMgr) {
      Schema schema = interceptor.process(cls, components);
      if (schema != null) {
        return schema;
      }
    }

    if (cls instanceof Class && requestResponse != null) {

      List<Type> beanProperties = null;
      switch (requestResponse) {
        case REQUEST:
          beanProperties = getRequestBeanTypes((Class) cls);
          break;
        case RESPONSE:
          beanProperties = getResponseBeanTypes((Class) cls);
          break;
        default:
      }

      Optional.ofNullable(beanProperties)
          .ifPresent(properties -> properties.forEach(type ->
              {
                if (type instanceof ParameterizedType) {
                  Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                  Arrays.stream(actualTypeArguments).forEach(arg -> getSchema(arg, components, requestResponse));
                }

                if (type instanceof Class) {
                  getSchema(type, components, requestResponse);
                }
              })
          );
    }

    Schema schema = PrimitiveType.createProperty(cls);
    if (schema == null) {
      schema = context
          .resolve(new AnnotatedType(cls));
    }

    if (schema == null) {
      if (cls == List.class) {
        schema = new ArraySchema();
        ((ArraySchema) schema).setItems(new ObjectSchema());
      }
    }

    if (components == null) {
      return schema;
    }

    // correct reference
    Schema refSchema = schema;

    if (shouldExtractRef(schema)) {
      ensureSchemaNameExist(schema);
      schema.$ref(null);
      components.addSchemas(schema.getName(), schema);
      refSchema = new Schema();
      refSchema.set$ref(RefUtils.constructRef(schema.getName()));
    }

    if (schema instanceof ArraySchema) {
      ArraySchema arraySchema = (ArraySchema) schema;
      Schema itemSchema = arraySchema.getItems();
      if (shouldExtractRef(itemSchema)) {
        ensureSchemaNameExist(itemSchema);
        itemSchema.$ref(null);
        components.addSchemas(itemSchema.getName(), itemSchema);

        Schema itemRefSchema = new Schema();
        itemRefSchema.set$ref(RefUtils.constructRef(itemSchema.getName()));
        arraySchema.setItems(itemRefSchema);
      }

      refSchema = arraySchema;
    }

    return refSchema;
  }

  private static void ensureSchemaNameExist(Schema schema) {
    if (schema.getName() != null) {
      return;
    }

    if (schema.get$ref() != null) {
      schema.setName((String) RefUtils.extractSimpleName(schema.get$ref()).getKey());
      return;
    }
  }

  public static boolean shouldExtractRef(Schema schema) {
    if (schema.getName() != null || schema.get$ref() != null) {
      return true;
    }
    return false;
  }


  public static ObjectMapper mapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return mapper;
  }

  public static List<Type> getRequestBeanTypes(Class cls) {
    if (cls.isPrimitive()) {
      return null;
    }
    Method[] declaredMethods = cls.getDeclaredMethods();
    List<Type> beanProperties = new ArrayList<>();

    for (Method method : declaredMethods) {

      /**
       * Meet the following requirements, can be considered a request bean property
       * 1. method modifiers is public and non-static
       * 2. method name should be setAbc or setABC, setabc is not a setter method
       * 3. return value of method is void
       * 4. method has only one parameter
       */
      if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()) && method.getName()
          .startsWith("set") && method.getName().length() > 3 && Character.isUpperCase(method.getName().charAt(3))
          && method.getReturnType()
          .equals(Void.TYPE) && method.getParameterCount() == 1) {
        beanProperties.add(method.getGenericParameterTypes()[0]);
      }
    }
    return beanProperties;
  }

  public static List<Type> getResponseBeanTypes(Class cls) {
    if (cls.isPrimitive()) {
      return null;
    }
    Method[] declaredMethods = cls.getDeclaredMethods();
    List<Type> beanPropertyTypes = new ArrayList<>();

    for (Method method : declaredMethods) {
      /**
       * Meet the following requirements, can be considered a response bean property
       * 1. method modifiers is public and non-static
       * 2. method name should be getAbc or getABC, getabc is not a getter method
       * 3. return value of method is not void
       * 4. method has no parameters
       */
      if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()) && method.getName()
          .startsWith("get") && method.getName().length() > 3 && Character.isUpperCase(method.getName().charAt(3))
          && !method.getReturnType()
          .equals(Void.TYPE) && method.getParameterCount() == 0) {
        beanPropertyTypes.add(method.getGenericReturnType());
      }
    }
    return beanPropertyTypes;
  }
}
