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
import java.util.function.Function;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.doDiffValidateListProperty;

public abstract class ListPropertyDiffValidator<T, P>
  extends OasObjectDiffValidatorTemplate<T> {

  private final List<? extends OasObjectDiffValidator<P>> elementDiffValidators;

  private final Function<P, ?> elementKeyMapper;

  protected ListPropertyDiffValidator(
    List<? extends OasObjectDiffValidator<P>> elementDiffValidators,
    Function<P, Object> elementKeyMapper) {
    this.elementDiffValidators = elementDiffValidators;
    this.elementKeyMapper = elementKeyMapper;
  }

  @Override
  public List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject) {

    return doDiffValidateListProperty(
      context,
      getListPropertyName(),
      leftLocation,
      getListProperty(leftOasObject),
      rightLocation,
      getListProperty(rightOasObject),
      getElementType(),
      elementKeyMapper,
      elementDiffValidators
    );

  }

  protected abstract List<P> getListProperty(T oasObject);

  protected abstract String getListPropertyName();

  protected abstract OasObjectType getElementType();
}
