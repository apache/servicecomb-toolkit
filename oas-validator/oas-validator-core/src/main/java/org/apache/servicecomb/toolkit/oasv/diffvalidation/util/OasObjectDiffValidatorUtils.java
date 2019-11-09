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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.util;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationException;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasObjectDiffValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public abstract class OasObjectDiffValidatorUtils {

  private OasObjectDiffValidatorUtils() {
    // singleton
  }

  /**
   * assert parameters is not null-malformed
   *
   * @param leftLocation
   * @param leftOasObject
   * @param rightLocation
   * @param rightOasObject
   * @param <T>
   */
  public static <T> void assertNullGood(
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject) {

    assertNullConsistent(leftLocation, leftOasObject, rightLocation, rightOasObject);

    if (leftOasObject == null && rightOasObject == null) {
      throw new OasDiffValidationException("leftLocation, leftOasObject, rightLocation, rightOasObject are all null");
    }
  }

  /**
   * @param context
   * @param leftLocation
   * @param leftOasObject  null-safe
   * @param rightLocation
   * @param rightOasObject null-safe
   * @param validators
   * @param <T>
   * @return
   */
  public static <T> List<OasDiffViolation> doDiffValidateProperty(
    OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation,
    T leftOasObject,
    OasObjectPropertyLocation rightLocation,
    T rightOasObject,
    List<? extends OasObjectDiffValidator<T>> validators) {

    assertNullConsistent(leftLocation, leftOasObject, rightLocation, rightOasObject);

    if (leftLocation == null && rightLocation == null) {
      return emptyList();
    }

    List<OasDiffViolation> violations = new ArrayList<>();
    for (OasObjectDiffValidator validator : validators) {
      violations.addAll(validator.validate(context, leftLocation, leftOasObject, rightLocation, rightOasObject));
    }
    return violations;
  }

  /**
   * @param context
   * @param listPropertyName
   * @param leftOwnerLocation
   * @param leftListProperty   null-safe
   * @param rightOwnerLocation
   * @param rightListProperty  null-safe
   * @param elementType
   * @param elementKeyMapper
   * @param validators
   * @param <T>
   * @return
   */
  public static <T> List<OasDiffViolation> doDiffValidateListProperty(
    OasDiffValidationContext context,
    String listPropertyName,
    OasObjectPropertyLocation leftOwnerLocation,
    List<T> leftListProperty,
    OasObjectPropertyLocation rightOwnerLocation,
    List<T> rightListProperty,
    OasObjectType elementType,
    Function<T, ?> elementKeyMapper,
    List<? extends OasObjectDiffValidator<T>> validators) {

    assertOwnerLocationNotNull(leftOwnerLocation, rightOwnerLocation);

    if (leftListProperty == null) {
      leftListProperty = emptyList();
    }

    if (rightListProperty == null) {
      rightListProperty = emptyList();
    }

    List<OasDiffViolation> violations = new ArrayList<>();

    for (int i = 0; i < leftListProperty.size(); i++) {
      T leftElement = leftListProperty.get(i);
      Object leftElementKey = elementKeyMapper.apply(leftElement);
      OasObjectPropertyLocation leftElementLoc =
        leftOwnerLocation.property(listPropertyName + "[" + i + "]", elementType);

      int rightElementIndex = indexOf(rightListProperty, leftElementKey, elementKeyMapper);
      if (rightElementIndex == -1) {
        violations.addAll(doDiffValidateProperty(context, leftElementLoc, leftElement, null, null, validators));
      } else {
        T rightElement = rightListProperty.get(rightElementIndex);
        OasObjectPropertyLocation rightElementLoc =
          rightOwnerLocation.property(listPropertyName + "[" + rightElementIndex + "]", elementType);
        violations.addAll(
          doDiffValidateProperty(context, leftElementLoc, leftElement, rightElementLoc, rightElement, validators));
      }
    }

    for (int i = 0; i < rightListProperty.size(); i++) {
      T rightElement = rightListProperty.get(i);
      Object rightElementKey = elementKeyMapper.apply(rightElement);
      OasObjectPropertyLocation rightElementLoc =
        rightOwnerLocation.property(listPropertyName + "[" + i + "]", elementType);

      int leftElementIndex = indexOf(leftListProperty, rightElementKey, elementKeyMapper);
      if (leftElementIndex != -1) {
        continue;
      }
      violations.addAll(doDiffValidateProperty(context, null, null, rightElementLoc, rightElement, validators));

    }
    return violations;

  }

  private static <T> int indexOf(List<T> elementList, Object searchKey, Function<T, ?> elementKeyMapper) {
    for (int i = 0; i < elementList.size(); i++) {
      T element = elementList.get(i);
      if (elementKeyMapper.apply(element).equals(searchKey)) {
        return i;
      }

    }
    return -1;
  }

  /**
   * @param context
   * @param mapPropertyName
   * @param leftOwnerLocation
   * @param leftMapProperty    null-safe
   * @param rightOwnerLocation
   * @param rightMapProperty   null-safe
   * @param valueType
   * @param validators
   * @param <T>
   * @return
   */
  public static <T> List<OasDiffViolation> doDiffValidateMapProperty(
    OasDiffValidationContext context,
    String mapPropertyName,
    OasObjectPropertyLocation leftOwnerLocation,
    Map<String, T> leftMapProperty,
    OasObjectPropertyLocation rightOwnerLocation,
    Map<String, T> rightMapProperty,
    OasObjectType valueType,
    List<? extends OasObjectDiffValidator<T>> validators
  ) {

    assertOwnerLocationNotNull(leftOwnerLocation, rightOwnerLocation);

    if (leftMapProperty == null) {
      leftMapProperty = emptyMap();
    }
    if (rightMapProperty == null) {
      rightMapProperty = emptyMap();
    }

    List<OasDiffViolation> violations = new ArrayList<>();

    for (Map.Entry<String, T> entry : leftMapProperty.entrySet()) {
      String leftKey = entry.getKey();
      T leftValue = entry.getValue();

      String keyName = mapPropertyName + ".'" + leftKey + "'";
      OasObjectPropertyLocation leftValueLoc = leftOwnerLocation.property(keyName, valueType);

      T rightValue = rightMapProperty.get(leftKey);
      if (rightValue == null) {
        violations.addAll(doDiffValidateProperty(context, leftValueLoc, leftValue, null, null, validators));
      } else {
        OasObjectPropertyLocation rightValueLoc = rightOwnerLocation.property(keyName, valueType);
        violations
          .addAll(doDiffValidateProperty(context, leftValueLoc, leftValue, rightValueLoc, rightValue, validators));
      }

    }

    for (Map.Entry<String, T> entry : rightMapProperty.entrySet()) {
      String rightKey = entry.getKey();
      if (leftMapProperty.containsKey(rightKey)) {
        continue;
      }

      T rightValue = entry.getValue();
      String keyName = mapPropertyName + ".'" + rightKey + "'";
      OasObjectPropertyLocation rightLoc = rightOwnerLocation.property(keyName, valueType);
      violations.addAll(doDiffValidateProperty(context, null, null, rightLoc, rightValue, validators));

    }
    return violations;
  }


  private static void assertOwnerLocationNotNull(
    OasObjectPropertyLocation leftOwnerLocation,
    OasObjectPropertyLocation rightOwnerLocation) {
    if (leftOwnerLocation == null) {
      throw new OasDiffValidationException("leftOwnerLocation is null");
    }
    if (rightOwnerLocation == null) {
      throw new OasDiffValidationException("rightOwnerLocation is null");
    }
  }

  private static <T> void assertNullConsistent(
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject) {

    if (leftLocation == null ^ leftOasObject == null) {
      throw new OasDiffValidationException("leftLocation, leftOasObject should be both null or not");
    }
    if (rightLocation == null ^ rightOasObject == null) {
      throw new OasDiffValidationException("rightLocation, rightOasObject should be both null or not");
    }
  }
}
