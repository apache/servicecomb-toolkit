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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ParameterDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.config.OasDiffValidatorsSkeletonConfiguration;

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.OasCompatibilityTestBase;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ParameterAllowReservedChangeDiffValidatorTest.TestConfiguration.class)
public class ParameterAllowReservedChangeDiffValidatorTest extends OasCompatibilityTestBase {

  @Test
  public void validate() {
  }

  /**
   * TODO swagger parser对于allowReserved字段的反序列化存在问题，先跳过此测试
   * https://github.com/swagger-api/swagger-parser/issues/1108
   */
  public void validateBroken() {
    OpenAPI leftOpenAPI = loadRelative("petstore-parameter-allow-reserved-a.yaml");
    System.out.println(leftOpenAPI.toString());
    OpenAPI rightOpenAPI = loadRelative("petstore-parameter-allow-reserved-b.yaml");
    List<OasDiffViolation> violations = oasSpecDiffValidator
      .validate(createContext(leftOpenAPI, rightOpenAPI), leftOpenAPI, rightOpenAPI);

    assertThat(violations)
      .containsExactlyInAnyOrder(
        createViolation(
          "[name=limit4,in=query]:仅允许false->true的修改",
          new Object[] {
            "paths", PATHS,
            "/pets", PATH_ITEM,
            "get", OPERATION,
            "parameters[1]", PARAMETER,
            "allowReserved", null
          },
          new Object[] {
            "paths", PATHS,
            "/pets", PATH_ITEM,
            "get", OPERATION,
            "parameters[2]", PARAMETER,
            "allowReserved", null
          }
        )
      );

  }

  @Configuration
  @Import(OasDiffValidatorsSkeletonConfiguration.class)
  public static class TestConfiguration {

    @Bean
    public ParameterDiffValidator parameterAllowReservedChangeDiffValidator() {

      return new ParameterAllowReservedChangeDiffValidator();
    }

  }

}

