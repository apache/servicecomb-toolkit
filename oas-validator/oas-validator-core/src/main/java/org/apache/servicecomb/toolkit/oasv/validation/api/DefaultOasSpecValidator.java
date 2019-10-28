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

package org.apache.servicecomb.toolkit.oasv.validation.api;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DefaultOasSpecValidator implements OasSpecValidator {

  private List<OpenApiValidator> openApiValidators;

  public DefaultOasSpecValidator(List<OpenApiValidator> openApiValidators) {
    this.openApiValidators = openApiValidators;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OpenAPI openAPI) {

    OasObjectPropertyLocation location = OasObjectPropertyLocation.root();

    return openApiValidators
      .stream()
      .map(validator -> validator.validate(context, location, openAPI))
      .flatMap(list -> list.stream())
      .collect(toList());

  }
}
