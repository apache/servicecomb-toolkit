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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.schema;

import org.apache.servicecomb.toolkit.oasv.compliance.validator.OasComplianceTestBase;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.SchemaValidator;
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
import static org.apache.servicecomb.toolkit.oasv.compliance.validator.schema.SchemaKeysValidators.PROPERTIES_LOWER_CAMEL_CASE_VALIDATOR;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = SchemaPropertiesKeysLowerCamelCaseValidatorTest.TestConfiguration.class)
public class SchemaPropertiesKeysLowerCamelCaseValidatorTest extends OasComplianceTestBase {

  @Test
  public void testValidateParameter1() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-param-1.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "paths", PATHS,
        "/pets", PATH_ITEM,
        "get", OPERATION,
        "parameters[0]", PARAMETER,
        "schema", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testValidateParameter2() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-param-2.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "parameters.'Foo'", PARAMETER,
        "schema", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testResponse1() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-resp-1.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "paths", PATHS,
        "/pets", PATH_ITEM,
        "get", OPERATION,
        "responses", RESPONSES,
        "200", RESPONSE,
        "content.'application/json'", MEDIA_TYPE,
        "schema", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testResponse2() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-resp-2.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "responses.'Foo'", RESPONSE,
        "content.'application/json'", MEDIA_TYPE,
        "schema", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testRequestBody1() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-req-1.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "paths", PATHS,
        "/pets", PATH_ITEM,
        "post", OPERATION,
        "requestBody", REQUEST_BODY,
        "content.'application/json'", MEDIA_TYPE,
        "schema", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testRequestBody2() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-req-2.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "requestBodies.'Foo'", REQUEST_BODY,
        "content.'application/json'", MEDIA_TYPE,
        "schema", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testComponent() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-comp.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testNested() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-nested.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "properties.'Foo'", null
      ),
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "properties.'Foo'", SCHEMA,
        "properties.'Bar'", null
      )
    );
  }

  @Test
  public void testAllOf() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-all-of.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "allOf[0]", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testAnyOf() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-any-of.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "anyOf[0]", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testOneOf() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-one-of.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "oneOf[0]", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Test
  public void testArray() {
    OpenAPI openAPI = loadRelative("petstore-schema-p-keys-lower-camel-case-array.yaml");
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    assertThat(violations).containsExactlyInAnyOrder(
      createViolation(
        ViolationMessages.LOWER_CAMEL_CASE,
        "components", COMPONENTS,
        "schemas.'Foo'", SCHEMA,
        "items", SCHEMA,
        "properties.'Foo'", null
      )
    );
  }

  @Configuration
  @Import({
    OasValidatorsSkeletonConfiguration.class
  })
  public static class TestConfiguration {

    @Bean

    public SchemaValidator schemaValidator() {

      return PROPERTIES_LOWER_CAMEL_CASE_VALIDATOR;
    }
  }

}
