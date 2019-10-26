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

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;

import org.apache.servicecomb.toolkit.generator.annotation.HttpMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathMethodAnnotationProcessor;
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

  class GetClass {
    @GET
    public String get() {
      return "get";
    }
  }
}
