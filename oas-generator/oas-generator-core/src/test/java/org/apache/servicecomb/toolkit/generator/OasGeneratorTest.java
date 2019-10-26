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
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem.HttpMethod;

public class OasGeneratorTest {

  @Test
  public void generatorOas() {

    Set<Class> classSet = new HashSet<>();
    classSet.add(NoResource.class);
    classSet.add(OneResource.class);
    OasGenerator generator = new OasGenerator();
    generator.generate(classSet);
  }

  @Test
  public void constructOasContext() throws NoSuchMethodException {
    OasContext oasContext = new OasContext(null);

    Method method = OneResource.class.getMethod("name", String.class);
    OperationContext operationContext = new OperationContext(method, oasContext);
    operationContext.setHttpMethod(HttpMethod.GET.name());
    operationContext.setOperationId(method.getName());
    operationContext.setPath("/operation");

    oasContext.addOperation(operationContext);
    oasContext.setCls(method.getDeclaringClass());
    oasContext.setBasePath("/oas");
    oasContext.setParser(null);

    Assert.assertEquals("/oas", oasContext.getBasePath());
    Assert.assertEquals(method.getDeclaringClass(), oasContext.getCls());
    Assert.assertNull(oasContext.getParser());

    Assert.assertEquals("/operation", operationContext.getPath());
    Assert.assertEquals(HttpMethod.GET.name(), operationContext.getHttpMethod());
    Assert.assertEquals(oasContext.getComponents(), operationContext.getComponents());
    Assert.assertEquals(null, operationContext.getApiResponses().getDefault());
    Assert.assertEquals(oasContext.getOpenAPI(), operationContext.getOpenAPI());

    Parameter parameter = method.getParameters()[0];
    ParameterContext parameterContext = new ParameterContext(operationContext, parameter);
    parameterContext.setName(parameter.getName());
    parameterContext.setRequired(true);
    parameterContext.setType(ParameterIn.QUERY.toString());

    Assert.assertEquals(parameter.getName(), parameterContext.getName());

    OpenAPI openAPI = oasContext.toOpenAPI();
    Assert.assertNotNull(openAPI);
  }

  class NoResource {

  }

  class OneResource {
    public String name(String name) {
      return name;
    }
  }
}
