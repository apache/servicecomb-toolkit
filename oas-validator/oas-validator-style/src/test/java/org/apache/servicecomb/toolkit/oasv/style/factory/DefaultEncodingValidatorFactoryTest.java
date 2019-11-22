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
import org.apache.servicecomb.toolkit.oasv.style.validator.encoding.EncodingHeadersKeysCaseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.EncodingValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.encoding.EncodingHeadersValuesValidator;
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
public class DefaultEncodingValidatorFactoryTest {

  @Autowired
  private DefaultEncodingValidatorFactory validatorFactory;

  @Test
  public void create() {
    FactoryOptions options = new FactoryOptions(emptyMap());
    List<EncodingValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(1);
    assertThat(validators).hasOnlyElementsOfTypes(
        EncodingHeadersValuesValidator.class
    );
  }

  @Test
  public void create1() {
    FactoryOptions options = new FactoryOptions(
        singletonMap(EncodingHeadersKeysCaseValidator.CONFIG_KEY, "upper-camel-case"));
    List<EncodingValidator> validators = validatorFactory.create(options);
    assertThat(validators).hasSize(2);
    assertThat(validators).hasOnlyElementsOfTypes(
        EncodingHeadersValuesValidator.class,
        EncodingHeadersKeysCaseValidator.class
    );
  }

}
