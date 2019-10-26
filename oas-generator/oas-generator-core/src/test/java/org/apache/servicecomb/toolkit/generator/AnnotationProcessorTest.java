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

import org.apache.servicecomb.toolkit.generator.annotation.ApiResponseMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ApiResponsesMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.OperationMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ParameterAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.parser.AbstractAnnotationParser;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.media.StringSchema;

public class AnnotationProcessorTest {

  @Test
  public void processApiResponseAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext context = new OperationContext(null, oasContext);
    ApiResponseMethodAnnotationProcessor apiResProcessor = new ApiResponseMethodAnnotationProcessor();
    ApiResponse apiResponse = Mockito.mock(ApiResponse.class);
    Content[] contents = new Content[1];
    contents[0] = Mockito.mock(Content.class);
    Mockito.when(contents[0].mediaType()).thenReturn(MediaTypeConst.APPLICATION_JSON);
    Mockito.when(apiResponse.content()).thenReturn(contents);
    Mockito.when(apiResponse.responseCode()).thenReturn("200");
    apiResProcessor.process(apiResponse, context);

    Assert.assertNotNull(context.getApiResponses().get("200"));
    Assert.assertNull(context.getApiResponses().get("500"));
  }

  @Test
  public void processApiResponsesAnnotation() {

    OasContext oasContext = new OasContext(new AbstractAnnotationParser() {
      @Override
      public int getOrder() {
        return 0;
      }

      @Override
      public boolean canProcess(Class<?> cls) {
        return true;
      }
    });

    OperationContext context = new OperationContext(null, oasContext);
    ApiResponsesMethodAnnotationProcessor apiRessProcessor = new ApiResponsesMethodAnnotationProcessor();
    ApiResponses apiResponses = Mockito.mock(ApiResponses.class);
    Content[] contents = new Content[1];
    contents[0] = Mockito.mock(Content.class);
    Mockito.when(contents[0].mediaType()).thenReturn(MediaTypeConst.APPLICATION_JSON);
    ApiResponse apiResponse = Mockito.mock(ApiResponse.class);
    Mockito.when(apiResponse.content()).thenReturn(contents);
    Mockito.when(apiResponse.responseCode()).thenReturn("200");
    Mockito.when(apiResponses.value()).thenReturn(new ApiResponse[] {apiResponse});

    apiRessProcessor.process(apiResponses, context);

    Assert.assertNotNull(context.getApiResponses().get("200"));
    Assert.assertNull(context.getApiResponses().get("500"));
  }

  @Test
  public void processOperationAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext context = new OperationContext(null, oasContext);
    OperationMethodAnnotationProcessor operationMethodAnnotationProcessor = new OperationMethodAnnotationProcessor();
    Operation operation = Mockito.mock(Operation.class);
    operationMethodAnnotationProcessor.process(operation, context);
  }


  @Test
  public void processParameterAnnotation() throws NoSuchMethodException, IllegalAccessException,
      InstantiationException {

    OasContext oasContext = new OasContext(null);
    Method parameterMethod = ParameterClass.class.getMethod("parameter", String.class);
    OperationContext operationContext = new OperationContext(parameterMethod, oasContext);
    java.lang.reflect.Parameter[] parameters = parameterMethod.getParameters();
    Assert.assertEquals(parameters.length, 1);
    java.lang.reflect.Parameter parameter = parameters[0];
    Parameter parameterDeclaredAnnotation = parameter.getDeclaredAnnotation(Parameter.class);

    ParameterContext parameterContext = new ParameterContext(operationContext, parameter);
    ParameterAnnotationProcessor parameterAnnotationProcessor = new ParameterAnnotationProcessor();

    parameterAnnotationProcessor.process(parameterDeclaredAnnotation, parameterContext);
    io.swagger.v3.oas.models.parameters.Parameter oasParameter = parameterContext.toOasParameter();
    Assert.assertEquals("param", oasParameter.getName());
    Assert.assertEquals(StringSchema.class, oasParameter.getSchema().getClass());
    Assert.assertTrue(parameterContext.isRequired());
    Assert.assertEquals(operationContext, parameterContext.getOperationContext());
    Assert.assertNull(parameterContext.getDefaultValue());
  }

  class ParameterClass {
    public void parameter(@Parameter(required = true) String param) {
    }
  }
}
