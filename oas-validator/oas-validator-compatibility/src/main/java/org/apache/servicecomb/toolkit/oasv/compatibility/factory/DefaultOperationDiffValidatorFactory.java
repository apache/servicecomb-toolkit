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

package org.apache.servicecomb.toolkit.oasv.compatibility.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.operation.OperationDeleteNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.operation.OperationIdNotSameDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OperationDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.OperationDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ParameterDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.RequestBodyDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ResponsesDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation.OperationParametersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation.OperationRequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.operation.OperationResponsesDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultOperationDiffValidatorFactory implements OperationDiffValidatorFactory {

  private final ParameterDiffValidatorFactory parameterDiffValidatorFactory;

  private final RequestBodyDiffValidatorFactory requestBodyDiffValidatorFactory;

  private final ResponsesDiffValidatorFactory responsesDiffValidatorFactory;

  public DefaultOperationDiffValidatorFactory(
      ParameterDiffValidatorFactory parameterDiffValidatorFactory,
      RequestBodyDiffValidatorFactory requestBodyDiffValidatorFactory,
      ResponsesDiffValidatorFactory responsesDiffValidatorFactory) {
    this.parameterDiffValidatorFactory = parameterDiffValidatorFactory;
    this.requestBodyDiffValidatorFactory = requestBodyDiffValidatorFactory;
    this.responsesDiffValidatorFactory = responsesDiffValidatorFactory;
  }

  @Override
  public List<OperationDiffValidator> create() {

    List<OperationDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new OperationParametersDiffValidator(parameterDiffValidatorFactory.create()));
    validators.add(new OperationRequestBodyDiffValidator(requestBodyDiffValidatorFactory.create()));
    validators.add(new OperationResponsesDiffValidator(responsesDiffValidatorFactory.create()));

    // concretes
    validators.add(new OperationDeleteNotAllowedDiffValidator());
    validators.add(new OperationIdNotSameDiffValidator());

    return Collections.unmodifiableList(validators);
  }
}
