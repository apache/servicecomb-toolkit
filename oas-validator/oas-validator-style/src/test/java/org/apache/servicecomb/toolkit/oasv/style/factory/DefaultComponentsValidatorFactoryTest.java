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

package org.apache.servicecomb.toolkit.oasv.style.factory;

import org.apache.servicecomb.toolkit.oasv.FactoryOptions;
import org.apache.servicecomb.toolkit.oasv.style.validator.components.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ValidatorFactoryComponents.class)
public class DefaultComponentsValidatorFactoryTest {

  @Autowired
  private DefaultComponentsValidatorFactory validatorFactory;

  @Test
  public void create() {
    FactoryOptions options = new FactoryOptions(emptyMap());
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(6);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class
    );
  }

  @Test
  public void create1() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsCallbacksKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsCallbacksKeysCaseValidator.class
    );
  }

  @Test
  public void create2() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsExamplesKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsExamplesKeysCaseValidator.class
    );
  }

  @Test
  public void create3() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsHeadersKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsHeadersKeysCaseValidator.class
    );
  }

  @Test
  public void create4() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsLinksKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsLinksKeysCaseValidator.class
    );
  }

  @Test
  public void create5() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsParametersKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsParametersKeysCaseValidator.class
    );
  }

  @Test
  public void create6() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsRequestBodiesKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsRequestBodiesKeysCaseValidator.class
    );
  }

  @Test
  public void create7() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsResponsesKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsResponsesKeysCaseValidator.class
    );
  }

  @Test
  public void create8() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsSchemasKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsSchemasKeysCaseValidator.class
    );
  }

  @Test
  public void create9() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ComponentsSecuritySchemesKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ComponentsValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(7);
    assertThat(validators).hasOnlyElementsOfTypes(
        ComponentsHeadersValuesValidator.class,
        ComponentsParametersValuesValidator.class,
        ComponentsRequestBodiesValuesValidator.class,
        ComponentsResponsesValuesValidator.class,
        ComponentsSchemasValuesValidator.class,
        ComponentsSecuritySchemesValuesValidator.class,
        ComponentsSecuritySchemesKeysCaseValidator.class
    );
  }
}
