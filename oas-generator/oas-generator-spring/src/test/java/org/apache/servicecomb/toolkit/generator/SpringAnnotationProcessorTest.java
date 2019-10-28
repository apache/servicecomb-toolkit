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

import org.apache.servicecomb.toolkit.generator.annotation.DeleteMappingMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.GetMappingMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathVariableAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PostMappingMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PutMappingMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestBodyAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestHeaderAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestMappingClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestMappingMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestPartAnnotationProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.models.media.FileSchema;

public class SpringAnnotationProcessorTest {

  @Test
  public void getHttpMethod() throws NoSuchMethodException {

    OasContext oasContext = new OasContext(null);
    Class<HttpMethodResource> httpMethodResourceClass = HttpMethodResource.class;
    RequestMapping requestMappingClassAnnotation = httpMethodResourceClass.getAnnotation(RequestMapping.class);
    RequestMappingClassAnnotationProcessor requestMappingClassAnnotationProcessor = new RequestMappingClassAnnotationProcessor();
    requestMappingClassAnnotationProcessor.process(requestMappingClassAnnotation, oasContext);
    Assert.assertEquals(requestMappingClassAnnotation.value()[0], oasContext.getBasePath());

    RequestMappingMethodAnnotationProcessor requestMappingMethodAnnotationProcessor = new RequestMappingMethodAnnotationProcessor();
    Method requestMethod = httpMethodResourceClass.getMethod("request");
    RequestMapping requestMappingMethodAnnotation = requestMethod.getAnnotation(RequestMapping.class);
    OperationContext requestOperationContext = new OperationContext(requestMethod, oasContext);
    requestMappingMethodAnnotationProcessor.process(requestMappingMethodAnnotation, requestOperationContext);
    Assert
        .assertEquals(requestMappingMethodAnnotation.value()[0],
            requestOperationContext.getPath());

    GetMappingMethodAnnotationProcessor getMappingMethodAnnotationProcessor = new GetMappingMethodAnnotationProcessor();
    Method getMethod = httpMethodResourceClass.getMethod("get");
    GetMapping getMappingAnnotation = getMethod.getAnnotation(GetMapping.class);
    OperationContext getOperationContext = new OperationContext(getMethod, oasContext);
    getMappingMethodAnnotationProcessor.process(getMappingAnnotation, getOperationContext);
    Assert
        .assertEquals(getMappingAnnotation.value()[0], getOperationContext.getPath());

    PostMappingMethodAnnotationProcessor postMappingMethodAnnotationProcessor = new PostMappingMethodAnnotationProcessor();
    Method postMethod = httpMethodResourceClass.getMethod("post");
    PostMapping postMappingAnnotation = postMethod.getAnnotation(PostMapping.class);
    OperationContext postOperationContext = new OperationContext(postMethod, oasContext);
    postMappingMethodAnnotationProcessor.process(postMappingAnnotation, postOperationContext);
    Assert
        .assertEquals(postMappingAnnotation.value()[0], postOperationContext.getPath());

    PutMappingMethodAnnotationProcessor putMappingMethodAnnotationProcessor = new PutMappingMethodAnnotationProcessor();
    Method putMethod = httpMethodResourceClass.getMethod("put");
    PutMapping putMappingAnnotation = putMethod.getAnnotation(PutMapping.class);
    OperationContext putOperationContext = new OperationContext(putMethod, oasContext);
    putMappingMethodAnnotationProcessor.process(putMappingAnnotation, putOperationContext);
    Assert
        .assertEquals(putMappingAnnotation.value()[0], putOperationContext.getPath());

    DeleteMappingMethodAnnotationProcessor deleteMappingMethodAnnotationProcessor = new DeleteMappingMethodAnnotationProcessor();
    Method deleteMethod = httpMethodResourceClass.getMethod("delete");
    DeleteMapping delelteMappingAnnotation = deleteMethod.getAnnotation(DeleteMapping.class);
    OperationContext deleteOperationContext = new OperationContext(deleteMethod, oasContext);
    deleteMappingMethodAnnotationProcessor.process(delelteMappingAnnotation, deleteOperationContext);
    Assert
        .assertEquals(delelteMappingAnnotation.value()[0], deleteOperationContext.getPath());
  }

  @Test
  public void parseParameter() throws NoSuchMethodException {
    Class<ParamAnnotationResource> paramAnnotationResourceClass = ParamAnnotationResource.class;
    OasContext oasContext = new OasContext(null);
    OperationContext operationContext;
    ParameterContext parameterContext;

    RequestParamAnnotationProcessor requestParamAnnotationProcessor = new RequestParamAnnotationProcessor();
    Method requestParamMethod = paramAnnotationResourceClass.getMethod("requestParam", String.class);
    Parameter requestParamMethodParam = requestParamMethod.getParameters()[0];
    RequestParam requestParamAnnotation = requestParamMethodParam
        .getAnnotation(RequestParam.class);
    operationContext = new OperationContext(requestParamMethod, oasContext);
    parameterContext = new ParameterContext(operationContext, requestParamMethodParam);
    requestParamAnnotationProcessor.process(requestParamAnnotation, parameterContext);
    io.swagger.v3.oas.models.parameters.Parameter oasParameter = parameterContext.toOasParameter();
    Assert.assertNull(parameterContext.getDefaultValue());
    Assert.assertNull(oasParameter.getSchema().getDefault());

    PathVariableAnnotationProcessor pathVariableAnnotationProcessor = new PathVariableAnnotationProcessor();
    Method pathVariableMethod = paramAnnotationResourceClass.getMethod("pathVariable", String.class);
    Parameter pathVariableMethodParam = pathVariableMethod.getParameters()[0];
    PathVariable pathVariableAnnotation = pathVariableMethodParam
        .getAnnotation(PathVariable.class);
    operationContext = new OperationContext(pathVariableMethod, oasContext);
    parameterContext = new ParameterContext(operationContext, pathVariableMethodParam);
    pathVariableAnnotationProcessor.process(pathVariableAnnotation, parameterContext);
    parameterContext.toOasParameter();
    Assert.assertTrue(parameterContext.isRequired());

    RequestPartAnnotationProcessor requestPartAnnotationProcessor = new RequestPartAnnotationProcessor();
    Method requestPartMethod = paramAnnotationResourceClass.getMethod("requestPart", MultipartFile.class);
    Parameter requestPartMethodParam = requestPartMethod.getParameters()[0];
    RequestPart requestPartParamAnnotation = requestPartMethodParam
        .getAnnotation(RequestPart.class);
    operationContext = new OperationContext(requestPartMethod, oasContext);
    parameterContext = new ParameterContext(operationContext, requestPartMethodParam);
    requestPartAnnotationProcessor.process(requestPartParamAnnotation, parameterContext);
    oasParameter = parameterContext.toOasParameter();
    Assert.assertNull(parameterContext.getDefaultValue());
    Assert.assertEquals(FileSchema.class, oasParameter.getSchema().getClass());

    RequestHeaderAnnotationProcessor requestHeaderAnnotationProcessor = new RequestHeaderAnnotationProcessor();
    Method requestHeaderMethod = paramAnnotationResourceClass.getMethod("requestHeader", String.class);
    Parameter requestHeaderMethodParam = requestHeaderMethod.getParameters()[0];
    RequestHeader requestHeaderParamAnnotation = requestHeaderMethodParam
        .getAnnotation(RequestHeader.class);
    operationContext = new OperationContext(requestPartMethod, oasContext);
    parameterContext = new ParameterContext(operationContext, requestHeaderMethodParam);
    requestHeaderAnnotationProcessor.process(requestHeaderParamAnnotation, parameterContext);
    oasParameter = parameterContext.toOasParameter();
    Assert.assertNull(parameterContext.getDefaultValue());
    Assert.assertNull(oasParameter.getSchema().getDefault());

    RequestBodyAnnotationProcessor requestBodyAnnotationProcessor = new RequestBodyAnnotationProcessor();
    Method requestBodyMethod = paramAnnotationResourceClass.getMethod("requestBody", String.class);
    Parameter requestBodyMethodParam = requestBodyMethod.getParameters()[0];
    RequestBody requestBodyParamAnnotation = requestBodyMethodParam
        .getAnnotation(RequestBody.class);
    operationContext = new OperationContext(requestBodyMethod, oasContext);
    parameterContext = new ParameterContext(operationContext, requestBodyMethodParam);
    requestBodyAnnotationProcessor.process(requestBodyParamAnnotation, parameterContext);
    parameterContext.toOasParameter();
    Assert.assertTrue(parameterContext.isRequired());
  }

  @Test
  public void interceptorModel() {
    MultipartFileInterceptor interceptor = new MultipartFileInterceptor();
    Assert.assertEquals(100, interceptor.order());
  }

  @RestController
  @RequestMapping("/path")
  class HttpMethodResource {

    @RequestMapping("/request")
    public String request() {
      return "request";
    }

    @GetMapping(value = "/get", consumes = {"application/json"})
    public String get() {
      return "get";
    }

    @PostMapping("/post")
    public String post() {
      return "post";
    }

    @PutMapping("/put")
    public String put() {
      return "put";
    }

    @DeleteMapping("/delete")
    public String delete() {
      return "delete";
    }
  }


  class ParamAnnotationResource {

    public void requestParam(@RequestParam("param") String param) {
    }

    public void requestBody(@RequestBody String param) {
    }

    public void requestHeader(@RequestHeader String headerParam) {
    }

    public void requestPart(@RequestPart MultipartFile file) {
    }

    public void pathVariable(@PathVariable String path) {
    }
  }
}
