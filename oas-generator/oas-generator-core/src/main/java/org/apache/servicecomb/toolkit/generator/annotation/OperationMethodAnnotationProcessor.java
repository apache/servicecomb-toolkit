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

package org.apache.servicecomb.toolkit.generator.annotation;

import org.apache.servicecomb.toolkit.generator.OperationContext;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;

public class OperationMethodAnnotationProcessor implements MethodAnnotationProcessor<Operation, OperationContext> {

  @Override
  public void process(Operation annotation, OperationContext context) {

    context.setOperationId(annotation.operationId());
    String s = annotation.operationId();
    boolean deprecated = annotation.deprecated();
    String description = annotation.description();
    Extension[] extensions = annotation.extensions();
    ExternalDocumentation externalDocumentation = annotation.externalDocs();
    RequestBody requestBody = annotation.requestBody();
    ApiResponse[] responses = annotation.responses();
    String method = annotation.method();
    Server[] servers = annotation.servers();
    SecurityRequirement[] security = annotation.security();
    String[] tags = annotation.tags();
    String summary = annotation.summary();
    Parameter[] parameters = annotation.parameters();

//    context.getOpenAPI().setPaths();

    // responseReference未解析
    // hidden未解析
    // authorizations未解析
  }
}
