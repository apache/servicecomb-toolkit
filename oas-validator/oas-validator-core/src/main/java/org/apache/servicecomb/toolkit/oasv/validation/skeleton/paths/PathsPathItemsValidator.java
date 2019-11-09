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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.paths;

import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathItemValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathsValidator;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils.doValidateProperty;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathsObject">Paths Object</a>
 * /{path} (Map [String, <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathItemObject">Path Item Object</a>])
 * validator
 */
public class PathsPathItemsValidator implements PathsValidator {

  private final List<PathItemValidator> pathItemValidators;

  public PathsPathItemsValidator(List<PathItemValidator> pathItemValidators) {
    this.pathItemValidators = pathItemValidators;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, Paths oasObject) {
    List<OasViolation> violations = new ArrayList<>();

    for (Map.Entry<String, PathItem> entry : oasObject.entrySet()) {
      String path = entry.getKey();
      PathItem pathItem = entry.getValue();
      OasObjectPropertyLocation pathItemLocation = location.property(path, OasObjectType.PATH_ITEM);
      violations.addAll(doValidateProperty(context, pathItemLocation, pathItem, pathItemValidators));
    }

    return violations;
  }

}
