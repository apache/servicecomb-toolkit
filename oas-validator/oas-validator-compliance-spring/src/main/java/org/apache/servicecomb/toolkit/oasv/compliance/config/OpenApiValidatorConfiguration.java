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

import org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi.OpenApiSecurityEmptyValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi.OpenApiTagNotEmptyValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi.OpenApiVersionValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiValidatorConfiguration {

  @Bean
  public OpenApiValidator openApiTagNotEmptyValidator() {
    return new OpenApiTagNotEmptyValidator();
  }

  @Bean
  public OpenApiValidator openApiVersionValidator() {
    return new OpenApiVersionValidator();
  }

  @Bean
  public OpenApiValidator openApiSecurityEmptyValidator() {
    return new OpenApiSecurityEmptyValidator();
  }

}
