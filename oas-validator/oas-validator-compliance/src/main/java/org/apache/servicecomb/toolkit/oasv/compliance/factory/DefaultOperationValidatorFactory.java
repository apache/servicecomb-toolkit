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

package org.apache.servicecomb.toolkit.oasv.compliance.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.servicecomb.toolkit.oasv.compliance.validator.operation.OperationIdLowerCamelCaseValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.operation.OperationServersEmptyValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.operation.OperationSummaryRequiredValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.operation.OperationTagsOnlyOneValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.operation.OperationTagsReferenceValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OperationValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OperationValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ParameterValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.RequestBodyValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ResponsesValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationParametersValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationRequestBodyValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationResponsesValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultOperationValidatorFactory implements OperationValidatorFactory {


  private final ParameterValidatorFactory parameterValidatorFactory;

  private final ResponsesValidatorFactory responsesValidatorFactory;

  private final RequestBodyValidatorFactory requestBodyValidatorFactory;

  public DefaultOperationValidatorFactory(
      ParameterValidatorFactory parameterValidatorFactory,
      ResponsesValidatorFactory responsesValidatorFactory,
      RequestBodyValidatorFactory requestBodyValidatorFactory) {
    this.parameterValidatorFactory = parameterValidatorFactory;
    this.responsesValidatorFactory = responsesValidatorFactory;
    this.requestBodyValidatorFactory = requestBodyValidatorFactory;
  }

  @Override
  public List<OperationValidator> create() {

    List<OperationValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new OperationParametersValidator(parameterValidatorFactory.create()));
    validators.add(new OperationResponsesValidator(responsesValidatorFactory.create()));
    validators.add(new OperationRequestBodyValidator(requestBodyValidatorFactory.create()));

    // concretes
    validators.add(new OperationIdLowerCamelCaseValidator());
    validators.add(new OperationSummaryRequiredValidator());
    validators.add(new OperationTagsOnlyOneValidator());
    validators.add(new OperationTagsReferenceValidator());
    validators.add(new OperationServersEmptyValidator());

    return Collections.unmodifiableList(validators);
  }
}
