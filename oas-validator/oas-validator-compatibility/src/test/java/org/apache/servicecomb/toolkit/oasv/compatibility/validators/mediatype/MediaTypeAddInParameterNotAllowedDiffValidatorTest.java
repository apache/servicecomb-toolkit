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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.DiffViolationMessages;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.MediaTypeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
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

@ContextConfiguration(classes = MediaTypeAddInParameterNotAllowedDiffValidatorTest.TestConfiguration.class)
public class MediaTypeAddInParameterNotAllowedDiffValidatorTest extends OasCompatibilityTestBase {

  @Test
  public void validate() {
    OpenAPI leftOpenAPI = loadRelative("petstore-media-type-add-in-parameter-a.yaml");
    OpenAPI rightOpenAPI = loadRelative("petstore-media-type-add-in-parameter-b.yaml");
    List<OasDiffViolation> violations = oasSpecDiffValidator
      .validate(createContext(leftOpenAPI, rightOpenAPI), leftOpenAPI, rightOpenAPI);

    assertThat(violations)
      .containsExactlyInAnyOrder(
        createViolationRight(
          DiffViolationMessages.OP_ADD_FORBIDDEN,
          new Object[] {
            "paths", PATHS,
            "/pets", PATH_ITEM,
            "get", OPERATION,
            "parameters[0]", PARAMETER,
            "content.'application/json'", MEDIA_TYPE
          }
        )
      );

  }

  @Configuration
  @Import(OasDiffValidatorsSkeletonConfiguration.class)
  public static class TestConfiguration {

    @Bean
    public MediaTypeDiffValidator mediaTypeAddInParameterNotAllowedDiffValidator() {

      return new MediaTypeAddInParameterNotAllowedDiffValidator();
    }

  }

}

