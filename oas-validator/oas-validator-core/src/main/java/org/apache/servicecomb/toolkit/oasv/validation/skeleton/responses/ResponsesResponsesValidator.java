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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.responses;

import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.ResponseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ResponsesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#responsesObject">Responses Object</a>
 * .default / .{Http Status Code} property validator
 */
public class ResponsesResponsesValidator implements ResponsesValidator {

  private final List<ResponseValidator> responseValidators;

  public ResponsesResponsesValidator(List<ResponseValidator> responseValidators) {
    this.responseValidators = responseValidators;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    ApiResponses oasObject) {

    List<OasViolation> violations = new ArrayList<>();

    for (Map.Entry<String, ApiResponse> entry : oasObject.entrySet()) {
      String statusCode = entry.getKey();
      ApiResponse response = entry.getValue();
      OasObjectPropertyLocation responseLoc = location.property(statusCode, OasObjectType.RESPONSE);
      violations.addAll(OasObjectValidatorUtils.doValidateProperty(context, responseLoc, response, responseValidators));
    }

    return violations;

  }
}
