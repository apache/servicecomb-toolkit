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

import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ObjectPropertyValidator;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.PATHS;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#openapi-object">OpenAPI Object</a>
 * .paths(<a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathsObject">Paths Object</a>)
 * validator
 */
public class OpenApiPathsValidator extends ObjectPropertyValidator<OpenAPI, Paths>
  implements OpenApiValidator {

  public OpenApiPathsValidator(List<PathsValidator> pathsValidators) {

    super(pathsValidators);
  }

  @Override
  protected String get$ref(OpenAPI oasObject) {
    return null;
  }

  @Override
  protected Paths getPropertyObject(OpenAPI oasObject) {
    return oasObject.getPaths();
  }

  @Override
  protected String getPropertyName() {
    return "paths";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return PATHS;
  }

}
