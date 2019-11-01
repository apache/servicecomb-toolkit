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

import org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter.ParameterCookieLowerCamelCaseValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter.ParameterDescriptionRequiredValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter.ParameterHeaderUpperHyphenCaseValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter.ParameterPathLowerCamelCaseValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter.ParameterQueryLowerCamelCaseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.MediaTypeValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ParameterValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.SchemaValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterSchemaValidator;
import org.springframework.stereotype.Component;

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
  public List<ParameterValidator> create() {

    List<ParameterValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ParameterSchemaValidator(schemaValidatorFactory.create()));
    validators.add(new ParameterContentValidator(mediaTypeValidatorFactory.create()));

    // concretes
    validators.add(new ParameterCookieLowerCamelCaseValidator());
    validators.add(new ParameterHeaderUpperHyphenCaseValidator());
    validators.add(new ParameterPathLowerCamelCaseValidator());
    validators.add(new ParameterQueryLowerCamelCaseValidator());
    validators.add(new ParameterDescriptionRequiredValidator());

    return Collections.unmodifiableList(validators);
  }
}
