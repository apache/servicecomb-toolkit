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

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.toolkit.generator.annotation.AnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ConsumesAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.CookieParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.FormParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.HeaderParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.HttpMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.QueryParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.context.OasContext;
import org.apache.servicecomb.toolkit.generator.context.OperationContext;
import org.apache.servicecomb.toolkit.generator.context.ParameterContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JaxrsAnnotationProcessorTest {

  @Test
  public void processApiResponseAnnotation() throws NoSuchMethodException {

    OasContext oasContext = new OasContext(null);
    OperationContext context = new OperationContext(null, oasContext);
    HttpMethodAnnotationProcessor httpMethodAnnotationProcessor = new HttpMethodAnnotationProcessor();

    GET get = GetClass.class.getMethod("get").getAnnotation(GET.class);
    httpMethodAnnotationProcessor.process(get, context);

    Assert.assertEquals(HttpMethod.GET, context.getHttpMethod());
  }

  @Test
  public void processPathClassAnnotation() {

    OasContext oasContext = new OasContext(null);
    PathClassAnnotationProcessor httpMethodAnnotationProcessor = new PathClassAnnotationProcessor();

    Path path = Mockito.mock(Path.class);
    Mockito.when(path.value()).thenReturn("/path");
    httpMethodAnnotationProcessor.process(path, oasContext);

    Assert.assertEquals("/path", oasContext.getBasePath());
  }

  @Test
  public void processPathMethodAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    PathMethodAnnotationProcessor pathMethodAnnotationProcessor = new PathMethodAnnotationProcessor();

    Path path = Mockito.mock(Path.class);
    Mockito.when(path.value()).thenReturn("/path");
    pathMethodAnnotationProcessor.process(path, operationContext);

    Assert.assertEquals("/path", operationContext.getPath());
  }

  @Test
  public void processConsumersAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    AnnotationProcessor annotationProcessor = new ConsumesAnnotationProcessor();

    Consumes consumes = Mockito.mock(Consumes.class);
    Mockito.when(consumes.value()).thenReturn(new String[] {MediaTypes.APPLICATION_JSON});
    annotationProcessor.process(consumes, operationContext);

    Assert.assertEquals(MediaTypes.APPLICATION_JSON, operationContext.getConsumers()[0]);
  }

  @Test
  public void processCookieParamAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    ParameterContext parameterContext = new ParameterContext(operationContext, null);
    AnnotationProcessor annotationProcessor = new CookieParamAnnotationProcessor();

    CookieParam cookieParam = Mockito.mock(CookieParam.class);
    Mockito.when(cookieParam.value()).thenReturn("param");
    annotationProcessor.process(cookieParam, parameterContext);

    Assert.assertEquals("param", parameterContext.getName());
  }

  @Test
  public void processFormParamAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    ParameterContext parameterContext = new ParameterContext(operationContext, null);
    AnnotationProcessor annotationProcessor = new FormParamAnnotationProcessor();

    FormParam formParam = Mockito.mock(FormParam.class);
    Mockito.when(formParam.value()).thenReturn("param");
    annotationProcessor.process(formParam, parameterContext);

    Assert.assertEquals("param", parameterContext.getName());
    Assert.assertTrue(parameterContext.isRequestBody());
  }

  @Test
  public void processHeaderParamAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    ParameterContext parameterContext = new ParameterContext(operationContext, null);
    AnnotationProcessor annotationProcessor = new HeaderParamAnnotationProcessor();

    HeaderParam headerParam = Mockito.mock(HeaderParam.class);
    Mockito.when(headerParam.value()).thenReturn("param");
    annotationProcessor.process(headerParam, parameterContext);

    Assert.assertEquals("param", parameterContext.getName());
    Assert.assertFalse(parameterContext.isRequestBody());
  }

  @Test
  public void processPathParamAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    ParameterContext parameterContext = new ParameterContext(operationContext, null);
    AnnotationProcessor annotationProcessor = new PathParamAnnotationProcessor();

    PathParam pathParam = Mockito.mock(PathParam.class);
    Mockito.when(pathParam.value()).thenReturn("param");
    annotationProcessor.process(pathParam, parameterContext);

    Assert.assertEquals("param", parameterContext.getName());
    Assert.assertFalse(parameterContext.isRequestBody());
  }


  @Test
  public void processQueryParamAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext operationContext = new OperationContext(null, oasContext);
    ParameterContext parameterContext = new ParameterContext(operationContext, null);
    AnnotationProcessor annotationProcessor = new QueryParamAnnotationProcessor();

    QueryParam queryParam = Mockito.mock(QueryParam.class);
    Mockito.when(queryParam.value()).thenReturn("param");
    annotationProcessor.process(queryParam, parameterContext);

    Assert.assertEquals("param", parameterContext.getName());
    Assert.assertFalse(parameterContext.isRequestBody());
  }

  class GetClass {
    @GET
    public String get() {
      return "get";
    }
  }
}
