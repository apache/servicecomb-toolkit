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
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.doDiffValidateProperty;

/**
 * OAS Object object property difference validator
 *
 * @param <T> Object defined in <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#table-of-contents">OpenAPI Specification</a>
 * @param <P> Property type
 */
public abstract class ObjectPropertyDiffValidator<T, P>
  extends OasObjectDiffValidatorTemplate<T> {

  private final List<? extends OasObjectDiffValidator<P>> diffValidators;

  protected ObjectPropertyDiffValidator(List<? extends OasObjectDiffValidator<P>> diffValidators) {
    this.diffValidators = diffValidators;
  }

  @Override
  protected List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject, OasObjectPropertyLocation rightLocation,
    T rightOasObject) {

    P leftProperty = getPropertyObject(leftOasObject);
    P rightProperty = getPropertyObject(rightOasObject);

    OasObjectPropertyLocation leftPropertyLoc =
      leftProperty == null ? null : leftLocation.property(getPropertyName(), getPropertyType());
    OasObjectPropertyLocation rightPropertyLoc =
      rightProperty == null ? null : rightLocation.property(getPropertyName(), getPropertyType());

    return doDiffValidateProperty(context, leftPropertyLoc, leftProperty, rightPropertyLoc, rightProperty,
      diffValidators);

  }

  /**
   * @param oasObject will never be null
   * @return
   */
  protected abstract P getPropertyObject(T oasObject);

  protected abstract String getPropertyName();

  protected abstract OasObjectType getPropertyType();

}
