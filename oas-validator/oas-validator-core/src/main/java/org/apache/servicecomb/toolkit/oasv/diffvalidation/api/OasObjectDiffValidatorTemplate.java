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

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.assertNullGood;
import static java.util.Collections.emptyList;

public class OasObjectDiffValidatorTemplate<T> implements OasObjectDiffValidator<T> {

  @Override
  public List<OasDiffViolation> validate(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject) {

    assertNullGood(leftLocation, leftOasObject, rightLocation, rightOasObject);
    if (leftOasObject == null && rightOasObject != null) {
      return validateAdd(context, rightLocation, rightOasObject);
    }
    if (leftOasObject != null && rightOasObject == null) {
      return validateDel(context, leftLocation, leftOasObject);
    }
    return validateCompare(context, leftLocation, leftOasObject, rightLocation, rightOasObject);
  }

  /**
   * validate the adding situation, left side doesn't exist but right side exists
   *
   * @param context
   * @param rightLocation
   * @param rightOasObject
   * @return
   */
  protected List<OasDiffViolation> validateAdd(OasDiffValidationContext context, OasObjectPropertyLocation rightLocation,
    T rightOasObject) {
    return emptyList();
  }

  /**
   * validate the deleting situation, left side exists but right side doesn't exist
   *
   * @param context
   * @param leftLocation
   * @param leftOasObject
   * @return
   */
  protected List<OasDiffViolation> validateDel(OasDiffValidationContext context, OasObjectPropertyLocation leftLocation,
    T leftOasObject) {
    return emptyList();
  }

  /**
   * validate the modification situation, both left side and right side right exists.
   * notice that left maybe equals to right
   *
   * @param context
   * @param leftLocation
   * @param leftOasObject
   * @param rightLocation
   * @param rightOasObject
   * @return
   */
  protected List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject) {
    return emptyList();
  }

}
