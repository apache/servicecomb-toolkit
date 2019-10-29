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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.operation;

import org.apache.servicecomb.toolkit.oasv.compliance.validator.OasComplianceTestBase;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OperationValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import org.apache.servicecomb.toolkit.oasv.validation.config.OasValidatorsSkeletonConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = OperationIdLowerCamelCaseValidatorTest.TestConfiguration.class)
public class OperationIdLowerCamelCaseValidatorTest extends OasComplianceTestBase {

  @Test
  public void testValidate() {
    OpenAPI openAPI = loadRelative("petstore-operation-id-lower-camel-case.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactly(
      createViolation(ViolationMessages.LOWER_CAMEL_CASE,
        "paths", PATHS,
        "/pets", PATH_ITEM,
        "get", OPERATION,
        "operationId", null));
  }

  @Configuration
  @Import({
    OasValidatorsSkeletonConfiguration.class
  })
  public static class TestConfiguration {

    @Bean

    public OperationValidator operationValidator() {
      return new OperationIdLowerCamelCaseValidator();
    }

  }

}

