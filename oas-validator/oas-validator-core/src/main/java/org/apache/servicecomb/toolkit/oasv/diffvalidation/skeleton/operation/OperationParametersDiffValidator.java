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

import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ListPropertyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OperationDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ParameterDiffValidator;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.PARAMETER;

public class OperationParametersDiffValidator
  extends ListPropertyDiffValidator<Operation, Parameter>
  implements OperationDiffValidator {

  public OperationParametersDiffValidator(List<ParameterDiffValidator> parameterValidators) {
    super(parameterValidators, parameter -> "in:" + parameter.getIn() + "/name:" + parameter.getName());
  }

  @Override
  protected List<Parameter> getListProperty(Operation oasObject) {
    return oasObject.getParameters();
  }

  protected String getListPropertyName() {
    return "parameters";
  }

  protected OasObjectType getElementType() {
    return PARAMETER;
  }

}
