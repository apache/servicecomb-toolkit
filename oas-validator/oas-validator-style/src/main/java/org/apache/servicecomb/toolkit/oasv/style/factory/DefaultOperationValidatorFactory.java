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

package org.apache.servicecomb.toolkit.oasv.style.factory;

import org.apache.servicecomb.toolkit.oasv.FactoryOptions;
import org.apache.servicecomb.toolkit.oasv.style.validator.operation.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.OperationValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OperationValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ParameterValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.RequestBodyValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ResponsesValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationParametersValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationRequestBodyValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationResponsesValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
  public List<OperationValidator> create(FactoryOptions options) {

    List<OperationValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new OperationParametersValidator(parameterValidatorFactory.create(options)));
    validators.add(new OperationResponsesValidator(responsesValidatorFactory.create(options)));
    validators.add(new OperationRequestBodyValidator(requestBodyValidatorFactory.create(options)));

    // concretes
    addOperationSummaryRequiredValidator(validators, options);
    addOperationIdCaseValidator(validators, options);
    addOperationTagsSizeEqValidator(validators, options);
    addOperationServersSizeEqValidator(validators, options);
    addOperationTagsReferenceValidator(validators, options);

    return Collections.unmodifiableList(validators);
  }

  private void addOperationSummaryRequiredValidator(List<OperationValidator> validators, FactoryOptions options) {
    Boolean required = options.getBoolean(OperationSummaryRequiredValidator.CONFIG_KEY);
    if (Boolean.TRUE.equals(required)) {
      validators.add(new OperationSummaryRequiredValidator());
    }
  }

  private void addOperationIdCaseValidator(List<OperationValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(OperationIdCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new OperationIdCaseValidator(expectedCase));
    }
  }

  private void addOperationTagsSizeEqValidator(List<OperationValidator> validators, FactoryOptions options) {
    Integer expectedSize = options.getInteger(OperationTagsSizeEqValidator.CONFIG_KEY);
    if (expectedSize != null) {
      validators.add(new OperationTagsSizeEqValidator(expectedSize));
    }
  }

  private void addOperationServersSizeEqValidator(List<OperationValidator> validators, FactoryOptions options) {
    Integer expectedSize = options.getInteger(OperationServersSizeEqValidator.CONFIG_KEY);
    if (expectedSize != null) {
      validators.add(new OperationServersSizeEqValidator(expectedSize));
    }
  }

  private void addOperationTagsReferenceValidator(List<OperationValidator> validators, FactoryOptions options) {
    Boolean needCheck = options.getBoolean(OperationTagsReferenceValidator.CONFIG_KEY);
    if (Boolean.TRUE.equals(needCheck)) {
      validators.add(new OperationTagsReferenceValidator());
    }
  }
}
