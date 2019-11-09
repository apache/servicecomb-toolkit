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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.pathitem;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils;
import org.apache.servicecomb.toolkit.oasv.validation.api.*;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathItemObject">Path Item Object</a>
 * .get / .put / .post / .delete / .options / .head / .patch / .trace
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#operationObject">Operation Object</a>
 * validator
 */
public class PathItemOperationsValidator implements PathItemValidator {

  private final List<OperationValidator> operationValidators;

  public PathItemOperationsValidator(List<OperationValidator> operationValidators) {
    this.operationValidators = operationValidators;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    PathItem oasObject) {
    if (StringUtils.isNotBlank(oasObject.get$ref())) {
      return emptyList();
    }

    List<OasViolation> violations = new ArrayList<>();

    Map<PathItem.HttpMethod, Operation> operationMap = oasObject.readOperationsMap();

    for (Map.Entry<PathItem.HttpMethod, Operation> entry : operationMap.entrySet()) {
      PathItem.HttpMethod method = entry.getKey();
      Operation operation = entry.getValue();
      OasObjectPropertyLocation operationLocation = location.property(method.toString().toLowerCase(), OasObjectType.OPERATION);
      violations.addAll(
        OasObjectValidatorUtils.doValidateProperty(context, operationLocation, operation, operationValidators));
    }
    return violations;
  }
}
