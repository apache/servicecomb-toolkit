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

import org.apache.servicecomb.toolkit.oasv.compliance.validator.components.ComponentsKeysValidators;
import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ComponentsValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.HeaderValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ParameterValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.RequestBodyValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ResponseValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.SchemaValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.SecuritySchemeValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsHeadersValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsParametersValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsRequestBodiesValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsResponsesValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsSchemasValuesValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsSecuritySchemesValuesValidator;
import org.springframework.stereotype.Component;


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
  public List<ComponentsValidator> create() {
    List<ComponentsValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ComponentsHeadersValuesValidator(headerValidatorFactory.create()));
    validators.add(new ComponentsParametersValuesValidator(parameterValidatorFactory.create()));
    validators.add(new ComponentsRequestBodiesValuesValidator(requestBodyValidatorFactory.create()));
    validators.add(new ComponentsResponsesValuesValidator(responseValidatorFactory.create()));
    validators.add(new ComponentsSchemasValuesValidator(schemaValidatorFactory.create()));
    validators.add(new ComponentsSecuritySchemesValuesValidator(securitySchemeValidatorFactory.create()));

    // concretes
    validators.add(ComponentsKeysValidators.CALLBACKS_UPPER_CAMEL_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.EXAMPLES_UPPER_CAMEL_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.HEADERS_UPPER_HYPHEN_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.LINKS_UPPER_CAMEL_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.PARAMETERS_UPPER_CAMEL_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.REQUEST_BODIES_UPPER_CAMEL_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.RESPONSES_UPPER_CAMEL_CASE_VALIDATOR);
    validators.add(ComponentsKeysValidators.SCHEMAS_UPPER_CAMEL_CASE_VALIDATOR);

    return Collections.unmodifiableList(validators);
  }
}
