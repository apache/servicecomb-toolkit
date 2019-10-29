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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.api;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DefaultOasSpecDiffValidator implements OasSpecDiffValidator {

  private final List<OpenApiDiffValidator> openApiDiffValidators;

  public DefaultOasSpecDiffValidator(List<OpenApiDiffValidator> openApiDiffValidators) {
    this.openApiDiffValidators = new ArrayList<>(openApiDiffValidators);
  }

  @Override
  public List<OasDiffViolation> validate(OasDiffValidationContext context, OpenAPI leftOpenApi, OpenAPI rightOpenApi) {

    OasObjectPropertyLocation leftRoot = OasObjectPropertyLocation.root();
    OasObjectPropertyLocation rightRoot = OasObjectPropertyLocation.root();

    return openApiDiffValidators
      .stream()
      .map(validator -> validator.validate(context, leftRoot, leftOpenApi, rightRoot, rightOpenApi))
      .flatMap(list -> list.stream())
      .collect(toList());
  }

}
