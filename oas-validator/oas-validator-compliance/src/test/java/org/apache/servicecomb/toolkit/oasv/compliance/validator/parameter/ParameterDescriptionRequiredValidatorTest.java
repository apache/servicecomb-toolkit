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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter;

import io.swagger.v3.oas.models.OpenAPI;
import org.apache.servicecomb.toolkit.oasv.compliance.factory.ValidatorFactoryComponents;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.OasStyleCheckTestBase;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasSpecValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ValidatorFactoryComponents.class)
public class ParameterDescriptionRequiredValidatorTest extends OasStyleCheckTestBase {

  @Test
  public void testValidate() {
    OpenAPI openAPI = loadRelative("petstore-parameter-desc-none.yaml");
    OasSpecValidator oasSpecValidator =
        oasSpecValidatorFactory.create(
            singleOption(ParameterDescriptionRequiredValidator.CONFIG_KEY, "true")
        );

    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.REQUIRED,
        "paths", PATHS,
        "/pets/{petId}", PATH_ITEM,
        "get", OPERATION,
        "parameters[0]", PARAMETER,
        "description", null
      ),
      createViolation(
        ViolationMessages.REQUIRED,
        "components", COMPONENTS,
        "parameters.'Bar'", PARAMETER,
        "description", null
      )
    );
  }

}

