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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.generator.parser.api.OpenApiAnnotationParser;
import org.apache.servicecomb.toolkit.generator.util.ModelConverter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

public class OperationContext {

  private OasContext parentContext;

  private Method method;

  private Operation operation = new Operation();

  private String operationId;

  private String path;

  private String httpMethod;

  private ApiResponses apiResponses = new ApiResponses();

  private List<ParameterContext> parameterContextList = new ArrayList<>();

  private OpenApiAnnotationParser parser;

  public OperationContext(Method method, OasContext parentContext) {
    this.parentContext = parentContext;
    this.method = method;
    this.parser = parentContext.getParser();
    this.parentContext.addOperation(this);
  }

  public void addParameter(ParameterContext context) {
    parameterContextList.add(context);
  }

  public OpenApiAnnotationParser getParser() {
    return parser;
  }

  public boolean hasOperation() {
    return httpMethod != null && method != null;
  }

  public Operation toOperation() {

    if (!hasOperation()) {
      return null;
    }

    if (StringUtils.isEmpty(operationId)) {
      operationId = method.getName();
    }

    operation.operationId(operationId);
    correctResponse(apiResponses);
    operation.setResponses(apiResponses);

    // 处理参数
    List<Parameter> parameterList = parameterContextList.stream()
        .map(parameterContext -> parameterContext.toOasParameter())
        .filter(parameter -> parameter != null)
        .collect(Collectors.toList());

    if (parameterList.size() > 0) {
      operation.parameters(parameterList);
    }

    return operation;
  }

  public void correctResponse(ApiResponses apiResponses) {

    if (apiResponses == null) {
      return;
    }
    // 处理响应
    // 没有注解被处理
    if (apiResponses.get(HttpStatus.OK) == null) {
      ApiResponse apiResponse = new ApiResponse();

      Class<?> returnType = method.getReturnType();
      if (returnType == Void.TYPE || returnType == Void.class) {
        return;
      }

      MediaType mediaType = new MediaType();

      Schema refSchema = ModelConverter.getSchema(returnType, getComponents());
      mediaType.schema(refSchema);

      Content content = new Content();
      content.addMediaType(MediaTypeConst.TEXT_PLAIN, mediaType);

      apiResponse.description("OK");
      apiResponse.setContent(content);
      apiResponses.addApiResponse(HttpStatus.OK, apiResponse);
    }
  }

  public Components getComponents() {
    return parentContext.getComponents();
  }

  public void addResponse(String key, ApiResponse response) {
    apiResponses.addApiResponse(key, response);
  }

  public ApiResponses getApiResponses() {
    return apiResponses;
  }

  public void setApiResponses(ApiResponses apiResponses) {
    this.apiResponses = apiResponses;
  }

  public String getOperationId() {
    return operationId;
  }

  public void setOperationId(String operationId) {
    this.operationId = operationId;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Operation getOperation() {
    return operation;
  }

  public OpenAPI getOpenAPI() {
    return parentContext.getOpenAPI();
  }

  public Method getMethod() {
    return method;
  }

  public OasContext getOpenApiContext() {
    return parentContext;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    if (this.httpMethod != null) {
      throw new IllegalArgumentException(String.format("too many http method in the method %s", method.getName()));
    }
    this.httpMethod = httpMethod.toUpperCase();
  }
}
