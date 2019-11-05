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

package org.apache.servicecomb.toolkit.oasv.compliance.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.EncodingValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.HeaderValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.InfoValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OperationValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.RequestBodyValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.SchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.TagValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
public class ValidatorConfigurationsTest implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Test
  public void testConfigurations() {
    assertThat(applicationContext.getBeansOfType(ComponentsValidator.class)).hasSize(14);
    assertThat(applicationContext.getBeansOfType(EncodingValidator.class)).hasSize(2);
    assertThat(applicationContext.getBeansOfType(HeaderValidator.class)).hasSize(2);
    assertThat(applicationContext.getBeansOfType(InfoValidator.class)).hasSize(1);
    assertThat(applicationContext.getBeansOfType(OpenApiValidator.class)).hasSize(8);
    assertThat(applicationContext.getBeansOfType(OperationValidator.class)).hasSize(8);
    assertThat(applicationContext.getBeansOfType(ParameterValidator.class)).hasSize(7);
    assertThat(applicationContext.getBeansOfType(PathsValidator.class)).hasSize(2);
    assertThat(applicationContext.getBeansOfType(RequestBodyValidator.class)).hasSize(2);
    assertThat(applicationContext.getBeansOfType(SchemaValidator.class)).hasSize(2);
    assertThat(applicationContext.getBeansOfType(TagValidator.class)).hasSize(3);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
