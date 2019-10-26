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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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

  public static Schema getSchema(Class<?> cls) {
    return getSchema(cls, null);
  }

  public static Schema getSchema(Class<?> cls, Components components) {

    for (ModelInterceptor interceptor : interceptorMgr) {
      Schema schema = interceptor.process(cls, components);
      if (schema != null) {
        return schema;
      }
    }

    Schema schema = PrimitiveType.createProperty(cls);
    if (schema == null) {
      schema = context
          .resolve(new AnnotatedType(cls));
    }

    if (components == null) {
      return schema;
    }

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
}
