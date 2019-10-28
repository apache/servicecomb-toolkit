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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.pathitem;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.*;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasDiffValidationContextUtils;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.OPERATION;
import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.doDiffValidateProperty;

public class PathItemOperationsDiffValidator
  extends OasObjectDiffValidatorTemplate<PathItem>
  implements PathItemDiffValidator {

  private final List<OperationDiffValidator> operationValidators;

  public PathItemOperationsDiffValidator(List<OperationDiffValidator> operationValidators) {
    this.operationValidators = operationValidators;
  }

  @Override
  public List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, PathItem leftOasObject,
    OasObjectPropertyLocation rightLocation, PathItem rightOasObject) {

    Map<PathItem.HttpMethod, Operation> leftOperationMap = leftOasObject.readOperationsMap();
    Map<PathItem.HttpMethod, Operation> rightOperationMap = rightOasObject.readOperationsMap();

    List<OasDiffViolation> violations = new ArrayList<>();

    for (Map.Entry<PathItem.HttpMethod, Operation> entry : leftOperationMap.entrySet()) {
      PathItem.HttpMethod leftMethod = entry.getKey();
      Operation leftOperation = entry.getValue();
      OasObjectPropertyLocation leftOperationLoc = leftLocation
        .property(leftMethod.toString().toLowerCase(), OPERATION);

      Operation rightOperation = rightOperationMap.get(leftMethod);
      if (rightOperation == null) {

        violations.addAll(
          doDiffValidateProperty(
            context,
            leftOperationLoc,
            leftOperation,
            null,
            null,
            operationValidators
          )
        );

      } else {

        OasObjectPropertyLocation rightOperationLoc = rightLocation
          .property(leftMethod.toString().toLowerCase(), OPERATION);
        violations.addAll(
          doDiffValidateProperty(
            context,
            leftOperationLoc,
            leftOperation,
            rightOperationLoc,
            rightOperation,
            operationValidators
          )
        );

      }

    }

    for (Map.Entry<PathItem.HttpMethod, Operation> entry : rightOperationMap.entrySet()) {
      PathItem.HttpMethod rightMethod = entry.getKey();
      if (leftOperationMap.containsKey(rightMethod)) {
        continue;
      }
      Operation rightOperation = rightOperationMap.get(rightMethod);
      OasObjectPropertyLocation rightOperationLoc = rightLocation
        .property(rightMethod.toString().toLowerCase(), OPERATION);

      violations.addAll(
        doDiffValidateProperty(
          context,
          null,
          null,
          rightOperationLoc,
          rightOperation,
          operationValidators
        )
      );

    }

    return violations;

  }

}
