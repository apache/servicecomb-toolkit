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
import org.apache.servicecomb.toolkit.oasv.style.validator.operation.*;
import org.apache.servicecomb.toolkit.oasv.validation.api.OperationValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationParametersValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationRequestBodyValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.operation.OperationResponsesValidator;
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
public class DefaultOperationValidatorFactoryTest {

  @Autowired
  private DefaultOperationValidatorFactory validatorFactory;

  @Test
  public void create() {
    FactoryOptions options = new FactoryOptions(emptyMap());
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class
    );
  }

  @Test
  public void create1() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationSummaryRequiredValidator.CONFIG_KEY, "true"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(4);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class,
        OperationSummaryRequiredValidator.class
    );
  }

  @Test
  public void create2() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationSummaryRequiredValidator.CONFIG_KEY, "false"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class
    );
  }

  @Test
  public void creat3() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationIdCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(4);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class,
        OperationIdCaseValidator.class
    );
  }

  @Test
  public void creat4() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationTagsSizeEqValidator.CONFIG_KEY, "1"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(4);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class,
        OperationTagsSizeEqValidator.class
    );
  }

  @Test
  public void create5() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationServersSizeEqValidator.CONFIG_KEY, "1"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(4);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class,
        OperationServersSizeEqValidator.class
    );
  }

  @Test
  public void create6() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationTagsReferenceValidator.CONFIG_KEY, "true"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(4);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class,
        OperationTagsReferenceValidator.class
    );
  }

  @Test
  public void create7() {
    FactoryOptions options = new FactoryOptions(singletonMap(OperationTagsReferenceValidator.CONFIG_KEY, "false"));
    List<OperationValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(3);
    assertThat(validators).hasOnlyElementsOfTypes(
        OperationParametersValidator.class,
        OperationResponsesValidator.class,
        OperationRequestBodyValidator.class
    );
  }
}
