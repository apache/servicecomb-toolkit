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
import org.apache.servicecomb.toolkit.oasv.style.validator.parameter.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.MediaTypeValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ParameterValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.SchemaValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterSchemaValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DefaultParameterValidatorFactory implements ParameterValidatorFactory {

  private final SchemaValidatorFactory schemaValidatorFactory;

  private final MediaTypeValidatorFactory mediaTypeValidatorFactory;

  public DefaultParameterValidatorFactory(
      SchemaValidatorFactory schemaValidatorFactory,
      MediaTypeValidatorFactory mediaTypeValidatorFactory) {
    this.schemaValidatorFactory = schemaValidatorFactory;
    this.mediaTypeValidatorFactory = mediaTypeValidatorFactory;
  }

  @Override
  public List<ParameterValidator> create(FactoryOptions options) {

    List<ParameterValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ParameterSchemaValidator(schemaValidatorFactory.create(options)));
    validators.add(new ParameterContentValidator(mediaTypeValidatorFactory.create(options)));

    // concretes
    addParameterNameCookieCaseValidator(validators, options);
    addParameterNameHeaderCaseValidator(validators, options);
    addParameterNamePathCaseValidator(validators, options);
    addParameterNameQueryCaseValidator(validators, options);
    addParameterDescriptionRequiredValidator(validators, options);

    return Collections.unmodifiableList(validators);
  }

  private void addParameterNameCookieCaseValidator(List<ParameterValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ParameterNameCookieCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ParameterNameCookieCaseValidator(expectedCase));
    }
  }

  private void addParameterNameHeaderCaseValidator(List<ParameterValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ParameterNameHeaderCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ParameterNameHeaderCaseValidator(expectedCase));
    }
  }

  private void addParameterNamePathCaseValidator(List<ParameterValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ParameterNamePathCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ParameterNamePathCaseValidator(expectedCase));
    }
  }

  private void addParameterNameQueryCaseValidator(List<ParameterValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ParameterNameQueryCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ParameterNameQueryCaseValidator(expectedCase));
    }
  }

  private void addParameterDescriptionRequiredValidator(List<ParameterValidator> validators, FactoryOptions options) {
    Boolean required = options.getBoolean(ParameterDescriptionRequiredValidator.CONFIG_KEY);
    if (Boolean.TRUE.equals(required)) {
      validators.add(new ParameterDescriptionRequiredValidator());
    }
  }

}
