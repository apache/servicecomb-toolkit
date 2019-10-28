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

package org.apache.servicecomb.toolkit.oasv.compliance.config;

import org.apache.servicecomb.toolkit.oasv.compliance.validator.components.ComponentsKeysValidators;
import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentsValidatorConfiguration {

  @Bean
  public ComponentsValidator componentsCallbacksUpperCamelCaseValidator() {
    return ComponentsKeysValidators.CALLBACKS_UPPER_CAMEL_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsExamplesUpperCamelCaseValidator() {
    return ComponentsKeysValidators.EXAMPLES_UPPER_CAMEL_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsHeadersUpperHyphenCaseValidator() {
    return ComponentsKeysValidators.HEADERS_UPPER_HYPHEN_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsLinksUpperCamelCaseValidator() {
    return ComponentsKeysValidators.LINKS_UPPER_CAMEL_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsParametersUpperCamelCaseValidator() {
    return ComponentsKeysValidators.PARAMETERS_UPPER_CAMEL_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsRequestBodiesUpperCamelCaseValidator() {
    return ComponentsKeysValidators.REQUEST_BODIES_UPPER_CAMEL_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsResponsesUpperCamelCaseValidator() {
    return ComponentsKeysValidators.RESPONSES_UPPER_CAMEL_CASE_VALIDATOR;
  }

  @Bean
  public ComponentsValidator componentsSchemasUpperCamelCaseValidator() {
    return ComponentsKeysValidators.SCHEMAS_UPPER_CAMEL_CASE_VALIDATOR;
  }

}
