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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.components;

import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import org.apache.servicecomb.toolkit.oasv.validation.config.OasValidatorsSkeletonConfiguration;


import org.apache.servicecomb.toolkit.oasv.compliance.validator.OasComplianceTestBase;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.COMPONENTS;
import static org.apache.servicecomb.toolkit.oasv.compliance.validator.components.ComponentsKeysValidators.EXAMPLES_UPPER_CAMEL_CASE_VALIDATOR;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ComponentsExamplesUpperCamelCaseValidatorTest.TestConfiguration.class)
public class ComponentsExamplesUpperCamelCaseValidatorTest extends OasComplianceTestBase {

  @Test
  public void testUpperCamelCase() {
    OpenAPI openAPI = loadRelative("petstore-examples-upper-camel-case.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations)
      .containsExactlyInAnyOrder(
        createViolation(ViolationMessages.UPPER_CAMEL_CASE,
          "components", COMPONENTS,
          "examples.'foo'", null)
      );
  }

  @Configuration
  @Import({
    OasValidatorsSkeletonConfiguration.class
  })
  public static class TestConfiguration {

    @Bean

    public ComponentsValidator componentsValidator() {


      return EXAMPLES_UPPER_CAMEL_CASE_VALIDATOR;
    }

  }

}

