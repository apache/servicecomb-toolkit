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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.paths;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.*;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.doDiffValidateProperty;
import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.PATH_ITEM;

public class PathsPathItemsDiffValidator
  extends OasObjectDiffValidatorTemplate<Paths>
  implements PathsDiffValidator {

  private final List<PathItemDiffValidator> pathItemValidators;

  public PathsPathItemsDiffValidator(List<PathItemDiffValidator> pathItemValidators) {
    this.pathItemValidators = pathItemValidators;
  }

  @Override
  public List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Paths leftOasObject,
    OasObjectPropertyLocation rightLocation, Paths rightOasObject) {

    List<OasDiffViolation> violations = new ArrayList<>();

    for (Map.Entry<String, PathItem> entry : leftOasObject.entrySet()) {
      String lPath = entry.getKey();
      PathItem lPathItem = entry.getValue();
      OasObjectPropertyLocation lPathItemLoc = leftLocation.property(lPath, PATH_ITEM);

      PathItem rPathItem = rightOasObject.get(lPath);
      if (rPathItem == null) {
        violations.addAll(doDiffValidateProperty(context, lPathItemLoc, lPathItem, null, null, pathItemValidators));
      } else {
        OasObjectPropertyLocation rPathItemLoc = rightLocation.property(lPath, PATH_ITEM);
        violations.addAll(
          doDiffValidateProperty(context, lPathItemLoc, lPathItem, rPathItemLoc, rPathItem, pathItemValidators));
      }

    }

    for (Map.Entry<String, PathItem> entry : rightOasObject.entrySet()) {
      String rPath = entry.getKey();
      if (leftOasObject.containsKey(rPath)) {
        continue;
      }
      PathItem rPathItem = entry.getValue();
      OasObjectPropertyLocation rPathItemLoc = leftLocation.property(rPath, PATH_ITEM);
      violations.addAll(doDiffValidateProperty(context, null, null, rPathItemLoc, rPathItem, pathItemValidators));
    }
    return violations;
  }

}
