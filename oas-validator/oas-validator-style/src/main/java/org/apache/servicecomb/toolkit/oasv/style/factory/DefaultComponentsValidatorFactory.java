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
import org.apache.servicecomb.toolkit.oasv.style.validator.components.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.*;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class DefaultComponentsValidatorFactory implements ComponentsValidatorFactory {

  private HeaderValidatorFactory headerValidatorFactory;

  private ParameterValidatorFactory parameterValidatorFactory;

  private RequestBodyValidatorFactory requestBodyValidatorFactory;

  private ResponseValidatorFactory responseValidatorFactory;

  private SchemaValidatorFactory schemaValidatorFactory;

  private SecuritySchemeValidatorFactory securitySchemeValidatorFactory;

  public DefaultComponentsValidatorFactory(
      HeaderValidatorFactory headerValidatorFactory,
      ParameterValidatorFactory parameterValidatorFactory,
      RequestBodyValidatorFactory requestBodyValidatorFactory,
      ResponseValidatorFactory responseValidatorFactory,
      SchemaValidatorFactory schemaValidatorFactory,
      SecuritySchemeValidatorFactory securitySchemeValidatorFactory) {
    this.headerValidatorFactory = headerValidatorFactory;
    this.parameterValidatorFactory = parameterValidatorFactory;
    this.requestBodyValidatorFactory = requestBodyValidatorFactory;
    this.responseValidatorFactory = responseValidatorFactory;
    this.schemaValidatorFactory = schemaValidatorFactory;
    this.securitySchemeValidatorFactory = securitySchemeValidatorFactory;
  }

  @Override
  public List<ComponentsValidator> create(FactoryOptions options) {
    List<ComponentsValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ComponentsHeadersValuesValidator(headerValidatorFactory.create(options)));
    validators.add(new ComponentsParametersValuesValidator(parameterValidatorFactory.create(options)));
    validators.add(new ComponentsRequestBodiesValuesValidator(requestBodyValidatorFactory.create(options)));
    validators.add(new ComponentsResponsesValuesValidator(responseValidatorFactory.create(options)));
    validators.add(new ComponentsSchemasValuesValidator(schemaValidatorFactory.create(options)));
    validators.add(new ComponentsSecuritySchemesValuesValidator(securitySchemeValidatorFactory.create(options)));

    // concretes
    addComponentsCallbacksKeysCaseValidator(validators, options);
    addComponentsExamplesKeysCaseValidator(validators, options);
    addComponentsHeadersKeysCaseValidator(validators, options);
    addComponentsLinksKeysCaseValidator(validators, options);
    addComponentsParametersKeysCaseValidator(validators, options);
    addComponentsRequestBodiesKeysCaseValidator(validators, options);
    addComponentsResponsesKeysCaseValidator(validators, options);
    addComponentsSchemasKeysCaseValidator(validators, options);
    addComponentsSecuritySchemesKeysCaseValidator(validators, options);
    return Collections.unmodifiableList(validators);
  }

  private void addComponentsCallbacksKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsCallbacksKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsCallbacksKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsExamplesKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsExamplesKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsExamplesKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsHeadersKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsHeadersKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsHeadersKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsLinksKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsLinksKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsLinksKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsParametersKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsParametersKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsParametersKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsRequestBodiesKeysCaseValidator(List<ComponentsValidator> validators,
      FactoryOptions options) {
    String expectedCase = options.getString(ComponentsRequestBodiesKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsRequestBodiesKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsResponsesKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsResponsesKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsResponsesKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsSchemasKeysCaseValidator(List<ComponentsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(ComponentsSchemasKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsSchemasKeysCaseValidator(expectedCase));
    }
  }

  private void addComponentsSecuritySchemesKeysCaseValidator(List<ComponentsValidator> validators,
      FactoryOptions options) {
    String expectedCase = options.getString(ComponentsSecuritySchemesKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new ComponentsSecuritySchemesKeysCaseValidator(expectedCase));
    }
  }
}
