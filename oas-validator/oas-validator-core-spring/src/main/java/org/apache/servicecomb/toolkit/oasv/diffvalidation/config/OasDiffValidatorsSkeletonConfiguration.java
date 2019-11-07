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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.config;

import java.util.List;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.CallbackDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ComponentsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.DefaultOasSpecDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.EncodingDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.HeaderDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.InfoDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.LinkDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.MediaTypeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasSpecDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OpenApiDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OperationDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ParameterDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.PathItemDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.PathsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.RequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ResponseDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ResponsesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaAddValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaCompareValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaDelValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ServerDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.TagDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsCallbacksDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsHeadersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsLinksDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsParametersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsRequestBodiesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsResponsesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.encoding.EncodingHeadersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.header.HeaderSchemaDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.mediatype.MediaTypeEncodingDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.mediatype.MediaTypeSchemaDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiComponentsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiInfoDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiPathsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiServersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiTagsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation.OperationParametersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation.OperationRequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation.OperationResponsesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.parameter.ParameterContentDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.parameter.ParameterSchemaDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.pathitem.PathItemOperationsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.pathitem.PathItemParametersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.paths.PathsPathItemsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.requestbody.RequestBodyContentDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.response.ResponseContentDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.response.ResponseHeadersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.responses.ResponsesResponsesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.schema.SchemaDiffValidatorEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OasDiffValidatorsSkeletonConfiguration {

  @Bean
  public OasSpecDiffValidator oasSpecDiffValidator(List<OpenApiDiffValidator> openApiDiffValidators) {
    return new DefaultOasSpecDiffValidator(openApiDiffValidators);
  }

  @Bean
  public ComponentsDiffValidator componentsCallbacksDiffValidator(
    List<CallbackDiffValidator> diffValidators) {
    return new ComponentsCallbacksDiffValidator(diffValidators);
  }

  @Bean
  public ComponentsDiffValidator componentsHeadersDiffValidator(
    List<HeaderDiffValidator> diffValidators) {
    return new ComponentsHeadersDiffValidator(diffValidators);
  }

  @Bean
  public ComponentsDiffValidator componentsLinksDiffValidator(
    List<LinkDiffValidator> diffValidators) {
    return new ComponentsLinksDiffValidator(diffValidators);
  }

  @Bean
  public ComponentsDiffValidator componentsParametersDiffValidator(
    List<ParameterDiffValidator> diffValidators) {
    return new ComponentsParametersDiffValidator(diffValidators);
  }

  @Bean
  public ComponentsDiffValidator componentsRequestBodiesDiffValidator(
    List<RequestBodyDiffValidator> diffValidators) {
    return new ComponentsRequestBodiesDiffValidator(diffValidators);
  }

  @Bean
  public ComponentsDiffValidator componentsResponsesDiffValidator(
    List<ResponseDiffValidator> diffValidators) {
    return new ComponentsResponsesDiffValidator(diffValidators);
  }

  @Bean
  public EncodingDiffValidator encodingHeadersDiffValidator(
    List<HeaderDiffValidator> diffValidators) {
    return new EncodingHeadersDiffValidator(diffValidators);
  }

  @Bean
  public HeaderDiffValidator headerSchemaDiffValidator(
    List<SchemaDiffValidator> diffValidators) {
    return new HeaderSchemaDiffValidator(diffValidators);
  }

  @Bean
  public MediaTypeDiffValidator mediaTypeEncodingDiffValidator(
    List<EncodingDiffValidator> diffValidators) {
    return new MediaTypeEncodingDiffValidator(diffValidators);
  }

  @Bean
  public MediaTypeDiffValidator mediaTypeSchemaDiffValidator(
    List<SchemaDiffValidator> diffValidators) {
    return new MediaTypeSchemaDiffValidator(diffValidators);
  }

  @Bean
  public OpenApiDiffValidator openApiComponentsDiffValidator(
    List<ComponentsDiffValidator> diffValidators) {
    return new OpenApiComponentsDiffValidator(diffValidators);
  }

  @Bean
  public OpenApiDiffValidator openApiInfoDiffValidator(
    List<InfoDiffValidator> diffValidators) {
    return new OpenApiInfoDiffValidator(diffValidators);
  }

  @Bean
  public OpenApiDiffValidator openApiPathsDiffValidator(
    List<PathsDiffValidator> diffValidators) {
    return new OpenApiPathsDiffValidator(diffValidators);
  }

  @Bean
  public OpenApiDiffValidator openApiServersDiffValidator(
    List<ServerDiffValidator> diffValidators) {
    return new OpenApiServersDiffValidator(diffValidators);
  }

  @Bean
  public OpenApiDiffValidator openApiTagsDiffValidator(
    List<TagDiffValidator> diffValidators) {
    return new OpenApiTagsDiffValidator(diffValidators);
  }

  @Bean
  public OperationDiffValidator operationParametersDiffValidator(
    List<ParameterDiffValidator> diffValidators) {
    return new OperationParametersDiffValidator(diffValidators);
  }

  @Bean
  public OperationDiffValidator operationRequestBodyDiffValidator(
    List<RequestBodyDiffValidator> diffValidators) {
    return new OperationRequestBodyDiffValidator(diffValidators);
  }

  @Bean
  public OperationDiffValidator operationResponsesDiffValidator(
    List<ResponsesDiffValidator> diffValidators) {
    return new OperationResponsesDiffValidator(diffValidators);
  }

  @Bean
  public ParameterDiffValidator parameterContentDiffValidator(
    List<MediaTypeDiffValidator> diffValidators) {
    return new ParameterContentDiffValidator(diffValidators);
  }

  @Bean
  public ParameterDiffValidator parameterSchemaDiffValidator(
    List<SchemaDiffValidator> diffValidators) {
    return new ParameterSchemaDiffValidator(diffValidators);
  }

  @Bean
  public PathItemDiffValidator pathItemOperationsDiffValidator(
    List<OperationDiffValidator> diffValidators) {
    return new PathItemOperationsDiffValidator(diffValidators);
  }

  @Bean
  public PathItemDiffValidator pathItemParametersDiffValidator(
    List<ParameterDiffValidator> diffValidators) {
    return new PathItemParametersDiffValidator(diffValidators);
  }

  @Bean
  public PathsDiffValidator pathsPathItemsDiffValidator(
    List<PathItemDiffValidator> diffValidators) {
    return new PathsPathItemsDiffValidator(diffValidators);
  }

  @Bean
  public RequestBodyDiffValidator requestBodyContentDiffValidator(
    List<MediaTypeDiffValidator> diffValidators) {
    return new RequestBodyContentDiffValidator(diffValidators);
  }

  @Bean
  public ResponseDiffValidator responseContentDiffValidator(
    List<MediaTypeDiffValidator> diffValidators) {
    return new ResponseContentDiffValidator(diffValidators);
  }

  @Bean
  public ResponseDiffValidator responseHeadersDiffValidator(
    List<HeaderDiffValidator> diffValidators) {
    return new ResponseHeadersDiffValidator(diffValidators);
  }

  @Bean
  public ResponsesDiffValidator responsesResponsesDiffValidator(
    List<ResponseDiffValidator> diffValidators) {
    return new ResponsesResponsesDiffValidator(diffValidators);
  }

  @Bean
  public SchemaDiffValidator schemaDiffValidatorEngine(
    List<SchemaAddValidator> schemaNewValidators,
    List<SchemaDelValidator> schemaDelValidators,
    List<SchemaCompareValidator> schemaCompareValidators) {
    return new SchemaDiffValidatorEngine(schemaNewValidators, schemaDelValidators, schemaCompareValidators);
  }

}
