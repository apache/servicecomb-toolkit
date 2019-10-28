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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ObjectPropertyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OperationDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.RequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.REQUEST_BODY;

public class OperationRequestBodyDiffValidator
  extends ObjectPropertyDiffValidator<Operation, RequestBody>
  implements OperationDiffValidator {

  public OperationRequestBodyDiffValidator(
    List<RequestBodyDiffValidator> requestBodyDiffValidators) {
    super(requestBodyDiffValidators);
  }

  @Override
  protected RequestBody getPropertyObject(Operation oasObject) {
    return oasObject.getRequestBody();
  }

  @Override
  protected String getPropertyName() {
    return "requestBody";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return REQUEST_BODY;
  }

}
