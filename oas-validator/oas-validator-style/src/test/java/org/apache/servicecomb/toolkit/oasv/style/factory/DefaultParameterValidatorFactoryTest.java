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
import org.apache.servicecomb.toolkit.oasv.style.validator.parameter.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter.ParameterSchemaValidator;
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
public class DefaultParameterValidatorFactoryTest {

  @Autowired
  private DefaultParameterValidatorFactory validatorFactory;

  @Test
  public void create() {
    FactoryOptions options = new FactoryOptions(emptyMap());
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(2);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class
    );
  }

  @Test
  public void create1() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ParameterNameCookieCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class,
        ParameterNameCookieCaseValidator.class
    );
  }

  @Test
  public void create2() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ParameterNameHeaderCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class,
        ParameterNameHeaderCaseValidator.class
    );
  }

  @Test
  public void create3() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ParameterNamePathCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class,
        ParameterNamePathCaseValidator.class
    );
  }

  @Test
  public void create4() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ParameterNameQueryCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class,
        ParameterNameQueryCaseValidator.class
    );
  }

  @Test
  public void create5() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ParameterDescriptionRequiredValidator.CONFIG_KEY, "true"));
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class,
        ParameterDescriptionRequiredValidator.class
    );
  }

  @Test
  public void create6() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(ParameterDescriptionRequiredValidator.CONFIG_KEY, "false"));
    List<ParameterValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(2);
    assertThat(validators).hasOnlyElementsOfTypes(
        ParameterSchemaValidator.class,
        ParameterContentValidator.class
    );
  }

}
