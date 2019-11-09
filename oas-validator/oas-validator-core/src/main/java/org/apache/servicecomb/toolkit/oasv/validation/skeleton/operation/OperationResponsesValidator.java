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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation;

import org.apache.servicecomb.toolkit.oasv.validation.api.OperationValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ResponsesValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.ObjectPropertyValidator;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.RESPONSES;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#operationObject">Operation Object</a>
 * .responses property validator
 */
public class OperationResponsesValidator extends ObjectPropertyValidator<Operation, ApiResponses>
  implements OperationValidator {

  public OperationResponsesValidator(List<ResponsesValidator> responseValidators) {
    super(responseValidators);
  }

  @Override
  protected String get$ref(Operation oasObject) {
    return null;
  }

  @Override
  protected ApiResponses getPropertyObject(Operation oasObject) {
    return oasObject.getResponses();
  }

  @Override
  protected String getPropertyName() {
    return "responses";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return RESPONSES;
  }

}
