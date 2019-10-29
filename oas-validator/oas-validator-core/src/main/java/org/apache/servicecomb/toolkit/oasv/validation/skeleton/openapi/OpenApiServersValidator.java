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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi;

import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ServerValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ListPropertyValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SERVER;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#openapi-object">OpenAPI Object</a>
 * .servers(List of <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#serverObject">Server Object</a>)校验器
 */
public class OpenApiServersValidator extends ListPropertyValidator<OpenAPI, Server>
  implements OpenApiValidator {

  public OpenApiServersValidator(List<ServerValidator> serverValidators) {
    super(serverValidators);
  }

  @Override
  protected String get$ref(OpenAPI oasObject) {
    return null;
  }

  @Override
  protected List<Server> getListProperty(OpenAPI oasObject) {
    return oasObject.getServers();
  }

  @Override
  protected String getListPropertyName() {
    return "servers";
  }

  @Override
  protected OasObjectType getElementType() {
    return SERVER;
  }

}
