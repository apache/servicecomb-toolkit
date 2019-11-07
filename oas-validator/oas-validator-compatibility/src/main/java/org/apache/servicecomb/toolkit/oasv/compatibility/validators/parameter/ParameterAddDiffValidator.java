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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasObjectDiffValidatorTemplate;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ParameterDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.util.ParameterUtils;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class ParameterAddDiffValidator
  extends OasObjectDiffValidatorTemplate<Parameter>
  implements ParameterDiffValidator {

  public static final String VIOLATION_MESSAGE = "required=true parameter is not allowed on right side";

  @Override
  protected List<OasDiffViolation> validateAdd(OasDiffValidationContext context,
    OasObjectPropertyLocation rightLocation, Parameter rightOasObject) {

    if (Boolean.FALSE.equals(rightOasObject.getRequired())) {
      return emptyList();
    }

    String message = new StringBuilder()
      .append(ParameterUtils.getKeyString(rightOasObject))
      .append(':')
      .append(VIOLATION_MESSAGE)
      .toString()
      ;

    return singletonList(OasDiffViolation.onlyRight(rightLocation.property("required"), message));

  }
}
