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

package org.apache.servicecomb.toolkit.generator.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.generator.HttpStatuses;
import org.apache.servicecomb.toolkit.generator.MediaTypes;
import org.apache.servicecomb.toolkit.generator.parser.api.OpenApiAnnotationParser;
import org.apache.servicecomb.toolkit.generator.util.ModelConverter;
import org.apache.servicecomb.toolkit.generator.util.RequestResponse;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

public class OperationContext implements IExtensionsContext {

  private static final RequestBody nullRequestBody = new RequestBody();

  private OasContext parentContext;

  private Method method;

  private Operation operation = new Operation();

  private String operationId;

  private String path;

  private String httpMethod;

  private ApiResponses apiResponses = new ApiResponses();

  private List<ParameterContext> parameterContexts = new ArrayList<>();

  private OpenApiAnnotationParser parser;

  private Boolean deprecated = false;

  private String description = null;

  private String summary;

  private List<String> tags;

  private String[] consumes;

  private String[] produces;

  private String[] headers;

  public OperationContext(Method method, OasContext parentContext) {
    this.parentContext = parentContext;
    this.method = method;
    this.parser = parentContext.getParser();
    this.parentContext.addOperation(this);
  }

  @Override
  public OpenApiAnnotationParser getParser() {
    return parser;
  }

  public boolean hasOperation() {
    return getHttpMethod() != null && method != null;
  }

  public Operation toOperation() {

    if (!hasOperation()) {
      return null;
    }

    if (StringUtils.isEmpty(operationId)) {
      operationId = method.getName();
    }

    operation.operationId(operationId);
    processHeaders();
    processProduces();
    correctResponse(apiResponses);
    operation.setResponses(apiResponses);

    RequestBody requestBody = new RequestBody();
    Content content = new Content();
    MediaType mediaType = new MediaType();

    // process parameter
    List<Parameter> parameterList = parameterContexts.stream()
        .map(parameterContext ->
        {
          // requestBody
          if (parameterContext.isRequestBody()) {

            Schema schema = mediaType.getSchema();
            if (schema == null) {
              schema = new ObjectSchema();
              mediaType.schema(schema);
            }
            schema.addProperties(parameterContext.getName(), parameterContext.getSchema());
            if (consumes != null) {
              for (String consume : getConsumers()) {
                content.addMediaType(consume, mediaType);
              }
            } else {
              if (parameterContext.getConsumers() != null && parameterContext.getConsumers().size() > 0) {
                for (String consume : parameterContext.getConsumers()) {
                  content.addMediaType(consume, mediaType);
                }
              } else {
                content.addMediaType(MediaTypes.APPLICATION_JSON, mediaType);
              }
            }

            requestBody.setContent(content);
            requestBody.setRequired(parameterContext.getRequired());
            return null;
          }

          // parameter
          return parameterContext.toParameter();
        })
        .filter(parameter -> parameter != null)
        .collect(Collectors.toList());

    if (parameterList.size() > 0) {
      operation.parameters(parameterList);
    }

    if (!nullRequestBody.equals(requestBody)) {
      operation.setRequestBody(requestBody);
    }
    return operation;
  }

  private void processHeaders() {

    if (getHeaders() == null) {
      return;
    }

    Arrays.stream(headers).forEach(header -> {
      String[] headMap = header.split("=");
      if (headMap.length == 2) {
        HeaderParameter headerParameter = new HeaderParameter();
        headerParameter.setName(headMap[0]);
        StringSchema value = new StringSchema();
        value.setDefault(headMap[1]);
        headerParameter.setSchema(value);
        operation.addParametersItem(headerParameter);
      }
    });
  }

  private void processProduces() {

    if (getProduces() == null) {
      return;
    }

    List<String> produceList = Arrays.stream(produces).filter(s -> !StringUtils.isEmpty(s))
        .collect(Collectors.toList());

    if (!produceList.isEmpty()) {
      ApiResponse apiResponse = new ApiResponse();
      Content content = new Content();
      MediaType mediaType = new MediaType();
      Schema schema = ModelConverter
          .getSchema(getMethod().getReturnType(), getComponents(), RequestResponse.RESPONSE);
      mediaType.schema(schema);
      for (String produce : produceList) {
        content.addMediaType(produce, mediaType);
      }
      apiResponse.description("OK");
      apiResponse.setContent(content);
      addResponse(HttpStatuses.OK, apiResponse);
    }
  }

  public void setRequestBody(RequestBody requestBody) {
    operation.requestBody(requestBody);
  }

  public void correctResponse(ApiResponses apiResponses) {

    if (apiResponses == null) {
      return;
    }

    // no annotations are processed
    // generate a default response based on the method return value
    if (apiResponses.get(HttpStatuses.OK) == null) {
      ApiResponse apiResponse = new ApiResponse();

      Class<?> returnType = method.getReturnType();
      if (returnType == Void.TYPE || returnType == Void.class) {
        return;
      }

      MediaType mediaType = new MediaType();

      Schema refSchema = ModelConverter.getSchema(returnType, getComponents(), RequestResponse.RESPONSE);
      mediaType.schema(refSchema);

      Content content = new Content();
      content.addMediaType(MediaTypes.APPLICATION_JSON, mediaType);
      apiResponse.description("OK");
      apiResponse.setContent(content);
      apiResponses.addApiResponse(HttpStatuses.OK, apiResponse);
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
    return Optional.ofNullable(httpMethod).orElse(parentContext.getHttpMethod());
  }

  public void setHttpMethod(String httpMethod) {
    if (this.httpMethod != null) {
      throw new IllegalArgumentException(String.format("too many http method in the method %s", method.getName()));
    }
    this.httpMethod = httpMethod.toUpperCase();
  }

  public Boolean getDeprecated() {
    return deprecated;
  }

  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void addTag(String tag) {
    if (tags == null) {
      tags = new ArrayList<>();
    }
    tags.add(tag);
  }

  @Override
  public void addExtension(String name, Object value) {
    operation.addExtension(name, value);
  }

  @Override
  public Map<String, Object> getExtensions() {
    return operation.getExtensions();
  }

  public String[] getConsumers() {
    return consumes;
  }

  public void setConsumers(String[] consumes) {
    this.consumes = consumes;
  }

  public void addParamCtx(ParameterContext ctx) {
    this.parameterContexts.add(ctx);
  }

  public String[] getProduces() {

    if (produces == null) {
      produces = parentContext.getProduces();
    }

    return produces;
  }

  public void setProduces(String[] produces) {
    this.produces = produces;
  }

  public String[] getHeaders() {

    if (headers == null) {
      headers = parentContext.getHeaders();
    }

    return headers;
  }

  public void setHeaders(String[] headers) {
    this.headers = headers;
  }
}
