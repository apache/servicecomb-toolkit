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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.requestbody;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.DiffViolationMessages;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasObjectDiffValidatorTemplate;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.RequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.util.ChangeRangeCheckUtils;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * RequestBody.required only allow change true-&gt;false
 */
public class RequestBodyRequiredChangeDiffValidator
  extends OasObjectDiffValidatorTemplate<RequestBody>
  implements RequestBodyDiffValidator {

  @Override
  protected List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, RequestBody leftOasObject, OasObjectPropertyLocation rightLocation,
    RequestBody rightOasObject) {

    if (ChangeRangeCheckUtils.isNotViolated(
      ObjectUtils.defaultIfNull(leftOasObject.getRequired(), Boolean.FALSE),
      ObjectUtils.defaultIfNull(rightOasObject.getRequired(), Boolean.FALSE),
      singletonList(new Object[] { true, false }))) {
      return emptyList();
    }

    return singletonList(new OasDiffViolation(
      leftLocation.property("required"),
      rightLocation.property("required"),
      DiffViolationMessages.TRUE_TO_FALSE
    ));

  }

}
