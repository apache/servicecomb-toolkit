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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.response;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.*;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation.onlyRight;
import static java.util.Collections.singletonList;

/**
 * Adding Response Object under responses.'http status code' is not allowed
 */
public class ResponseAddNotAllowedDiffValidator
  extends OasObjectDiffValidatorTemplate<ApiResponse>
  implements ResponseDiffValidator {

  @Override
  protected List<OasDiffViolation> validateAdd(OasDiffValidationContext context,
    OasObjectPropertyLocation rightLocation, ApiResponse rightOasObject) {
    return singletonList(onlyRight(rightLocation, DiffViolationMessages.OP_ADD_FORBIDDEN));
  }
}
