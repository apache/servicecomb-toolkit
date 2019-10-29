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

package org.apache.servicecomb.toolkit.oasv.validation.config;

import org.apache.servicecomb.toolkit.oasv.validation.api.*;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.*;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.encoding.EncodingHeadersValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.header.HeaderSchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.mediatype.MediaTypeEncodingValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.mediatype.MediaTypeSchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.*;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationParametersValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationRequestBodyValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationResponsesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterSchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.pathitem.PathItemOperationsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.pathitem.PathItemParametersValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.paths.PathsPathItemsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.requestbody.RequestBodyContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.response.ResponseContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.response.ResponseHeadersValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.responses.ResponsesResponsesValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OAS骨架Validator配置，骨架Validator只那些自己没有验证逻辑，只是将OAS Object属性交给对应OAS Validator校验的Validator
 */
@Configuration
public class OasValidatorsSkeletonConfiguration {

  @Bean
  public OasSpecValidator oasSpecValidator(
    List<OpenApiValidator> openApiValidators) {
    return new DefaultOasSpecValidator(openApiValidators);
  }

  @Bean
  public ComponentsValidator componentsHeadersValuesValidator(
    List<HeaderValidator> headerValidators) {
    return new ComponentsHeadersValuesValidator(headerValidators);
  }

  @Bean
  public ComponentsValidator componentsParametersValuesValidator(
    List<ParameterValidator> parameterValidators) {
    return new ComponentsParametersValuesValidator(parameterValidators);
  }

  @Bean
  public ComponentsValidator componentsRequestBodiesValuesValidator(
    List<RequestBodyValidator> requestBodyValidators) {
    return new ComponentsRequestBodiesValuesValidator(requestBodyValidators);
  }

  @Bean
  public ComponentsValidator componentsResponsesValuesValidator(
    List<ResponseValidator> responseValidators) {
    return new ComponentsResponsesValuesValidator(responseValidators);
  }

  @Bean
  public ComponentsValidator componentsSchemasValuesValidator(
    List<SchemaValidator> schemaValidators) {
    return new ComponentsSchemasValuesValidator(schemaValidators);
  }

  @Bean
  public ComponentsValidator componentSecuritySchemesValidator(
    List<SecuritySchemeValidator> securitySchemeValidators) {
    return new ComponentsSecuritySchemesValuesValidator(securitySchemeValidators);
  }

  @Bean
  public EncodingValidator encodingHeadersValuesValidator(
    List<HeaderValidator> headerValidators) {
    return new EncodingHeadersValuesValidator(headerValidators);
  }

  @Bean
  public HeaderValidator headerSchemaValidator(
    List<SchemaValidator> schemaValidators) {
    return new HeaderSchemaValidator(schemaValidators);
  }

  @Bean
  public MediaTypeValidator mediaTypeSchemaValidator(
    List<SchemaValidator> schemaValidators) {
    return new MediaTypeSchemaValidator(schemaValidators);
  }

  @Bean
  public MediaTypeValidator mediaTypeEncodingValidator(
    List<EncodingValidator> encodingValidators) {
    return new MediaTypeEncodingValidator(encodingValidators);
  }

  @Bean
  public OpenApiValidator openApiComponentsValidator(
    List<ComponentsValidator> componentsValidators) {
    return new OpenApiComponentsValidator(componentsValidators);
  }

  @Bean
  public OpenApiValidator openApiInfoValidator(
    List<InfoValidator> infoValidators) {
    return new OpenApiInfoValidator(infoValidators);
  }

  @Bean
  public OpenApiValidator openApiPathsValidator(
    List<PathsValidator> pathsValidators) {
    return new OpenApiPathsValidator(pathsValidators);
  }

  @Bean
  public OpenApiValidator openApiServersValidator(
    List<ServerValidator> serverValidators) {
    return new OpenApiServersValidator(serverValidators);
  }

  @Bean
  public OpenApiValidator openApiTagsValidator(
    List<TagValidator> tagValidators) {
    return new OpenApiTagsValidator(tagValidators);
  }

  @Bean
  public OperationValidator operationParametersValidator(
    List<ParameterValidator> parameterValidators) {
    return new OperationParametersValidator(parameterValidators);
  }

  @Bean
  public OperationValidator operationResponsesValidator(
    List<ResponsesValidator> responsesValidators) {
    return new OperationResponsesValidator(responsesValidators);
  }

  @Bean
  public OperationValidator operationRequestBodyValidator(
    List<RequestBodyValidator> requestBodyValidators) {
    return new OperationRequestBodyValidator(requestBodyValidators);
  }

  @Bean
  public ParameterValidator parameterSchemaValidator(
    List<SchemaValidator> schemaValidators) {
    return new ParameterSchemaValidator(schemaValidators);
  }

  @Bean
  public ParameterValidator parameterContentMediaTypeValidator(
    List<MediaTypeValidator> mediaTypeValidators) {
    return new ParameterContentValidator(mediaTypeValidators);
  }

  @Bean
  public PathItemValidator pathItemOperationsValidator(
    List<OperationValidator> operationValidators) {
    return new PathItemOperationsValidator(operationValidators);
  }

  @Bean
  public PathItemValidator pathItemParametersValidator(
    List<ParameterValidator> parameterValidators) {
    return new PathItemParametersValidator(parameterValidators);
  }

  @Bean
  public PathsValidator pathsPathItemsValidator(
    List<PathItemValidator> pathItemValidators) {
    return new PathsPathItemsValidator(pathItemValidators);
  }

  @Bean
  public RequestBodyValidator requestBodyContentValidator(
    List<MediaTypeValidator> mediaTypeValidators) {
    return new RequestBodyContentValidator(mediaTypeValidators);
  }

  @Bean
  public ResponsesValidator responsesResponsesValidator(
    List<ResponseValidator> responseValidators) {
    return new ResponsesResponsesValidator(responseValidators);
  }

  @Bean
  public ResponseValidator responseContentValidator(
    List<MediaTypeValidator> mediaTypeValidators) {
    return new ResponseContentValidator(mediaTypeValidators);
  }

  @Bean
  public ResponseValidator responseHeadersValuesValidator(
    List<HeaderValidator> headerValidators) {
    return new ResponseHeadersValuesValidator(headerValidators);
  }

}
