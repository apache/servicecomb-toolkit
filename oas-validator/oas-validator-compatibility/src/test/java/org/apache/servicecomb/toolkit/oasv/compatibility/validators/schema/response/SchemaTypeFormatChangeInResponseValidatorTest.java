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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaCompareValidator;
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

@ContextConfiguration(classes = SchemaTypeFormatChangeInResponseValidatorTest.TestConfiguration.class)
public class SchemaTypeFormatChangeInResponseValidatorTest extends OasCompatibilityTestBase {

  @Test
  public void validate() {
    OpenAPI leftOpenAPI = loadRelative("petstore-schema-type-format-in-response-a.yaml");
    OpenAPI rightOpenAPI = loadRelative("petstore-schema-type-format-in-response-b.yaml");
    List<OasDiffViolation> violations = oasSpecDiffValidator
      .validate(createContext(leftOpenAPI, rightOpenAPI), leftOpenAPI, rightOpenAPI);

    assertThat(violations)
      .containsExactlyInAnyOrder(
        createViolationBoth(
          "the change (type=integer,format=int32)->(type=integer,format=int64) is not allowed",
          new Object[] {
            "paths", PATHS,
            "/pets", PATH_ITEM,
            "get", OPERATION,
            "responses", RESPONSES,
            "200", RESPONSE,
            "content.'application/xml'", MEDIA_TYPE,
            "schema", SCHEMA,
            "properties.'foo'", SCHEMA
          }
        )
      );

  }

  @Configuration
  @Import(OasDiffValidatorsSkeletonConfiguration.class)
  public static class TestConfiguration {

    @Bean
    public SchemaCompareValidator schemaTypeFormatInResponseCompareValidator() {

      return new SchemaTypeFormatChangeInResponseValidator();
    }

  }

}


