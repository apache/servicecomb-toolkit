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

package org.apache.servicecomb.toolkit.generator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.servicecomb.toolkit.generator.annotation.ModelInterceptor;
import org.apache.servicecomb.toolkit.generator.util.ModelConverter;
import org.apache.servicecomb.toolkit.generator.util.ParamUtils;
import org.junit.Assert;
import org.junit.Test;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

public class UtilsTest {

  @Test
  public void getParameterName() throws NoSuchMethodException {

    Method method = ParameterClass.class.getMethod("method", String.class);
    Parameter parameter = method.getParameters()[0];
    String paramterName = ParamUtils.getParamterName(method, parameter);
    Assert.assertEquals("param", paramterName);
  }

  @Test
  public void getSchema() {
    Schema schema = ModelConverter.getSchema(String.class);
    Assert.assertEquals(StringSchema.class, schema.getClass());

    schema = ModelConverter.getSchema(ParameterClass.class);
    Assert.assertEquals(Schema.class, schema.getClass());

    schema = ModelConverter.getSchema(String[].class);
    Assert.assertEquals(ArraySchema.class, schema.getClass());

    Components components = new Components();
    schema = ModelConverter.getSchema(ParameterClass[].class, components);
    Assert.assertEquals(ArraySchema.class, schema.getClass());

    schema = ModelConverter.getSchema(ParameterClass.class, components);
    Assert.assertNotNull(schema.get$ref());

    ModelConverter.registerInterceptor(new ModelInterceptor() {
      @Override
      public int order() {
        return 0;
      }

      @Override
      public Schema process(Class<?> cls, Components components) {
        return new Schema().name("unknown");
      }
    });

    schema = ModelConverter.getSchema(ParameterClass.class);
    Assert.assertEquals("unknown", schema.getName());
  }

  class ParameterClass {
    public void method(String param) {
    }
  }
}
