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

import org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterValidatorConfiguration {

  @Bean
  public ParameterValidator parameterCookieLowerCamelCaseValidator() {
    return new ParameterCookieLowerCamelCaseValidator();
  }

  @Bean
  public ParameterValidator parameterHeaderUpperHyphenCaseValidator() {
    return new ParameterHeaderUpperHyphenCaseValidator();
  }

  @Bean
  public ParameterValidator parameterPathLowerCamelCaseValidator() {
    return new ParameterPathLowerCamelCaseValidator();
  }

  @Bean
  public ParameterValidator parameterQueryLowerCamelCaseValidator() {
    return new ParameterQueryLowerCamelCaseValidator();
  }

  @Bean
  public ParameterValidator parameterDescriptionRequiredValidator() {
    return new ParameterDescriptionRequiredValidator();
  }

}
