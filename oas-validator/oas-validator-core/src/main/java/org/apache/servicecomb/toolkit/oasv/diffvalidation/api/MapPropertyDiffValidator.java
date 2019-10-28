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
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.doDiffValidateMapProperty;

public abstract class MapPropertyDiffValidator<T, P>
  extends OasObjectDiffValidatorTemplate<T> {

  private final List<? extends OasObjectDiffValidator<P>> valueDiffValidators;

  protected MapPropertyDiffValidator(List<? extends OasObjectDiffValidator<P>> valueDiffValidators) {
    this.valueDiffValidators = valueDiffValidators;
  }

  @Override
  protected List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject, OasObjectPropertyLocation rightLocation,
    T rightOasObject) {

    return doDiffValidateMapProperty(
      context,
      getMapPropertyName(),
      leftLocation,
      getMapProperty(leftOasObject),
      rightLocation,
      getMapProperty(rightOasObject),
      getValueType(),
      valueDiffValidators
    );

  }

  protected abstract Map<String, P> getMapProperty(T oasObject);

  protected abstract String getMapPropertyName();

  protected abstract OasObjectType getValueType();
}
