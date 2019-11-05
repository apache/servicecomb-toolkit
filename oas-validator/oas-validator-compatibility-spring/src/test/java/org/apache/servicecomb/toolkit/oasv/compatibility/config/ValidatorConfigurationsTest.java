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

package org.apache.servicecomb.toolkit.oasv.compatibility.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.EncodingDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.HeaderDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.MediaTypeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OperationDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ParameterDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.PathItemDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.RequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ResponseDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaDiffValidator;
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
    assertThat(applicationContext.getBeansOfType(EncodingDiffValidator.class)).hasSize(7);
    assertThat(applicationContext.getBeansOfType(HeaderDiffValidator.class)).hasSize(3);
    assertThat(applicationContext.getBeansOfType(MediaTypeDiffValidator.class)).hasSize(6);
    assertThat(applicationContext.getBeansOfType(OperationDiffValidator.class)).hasSize(5);
    assertThat(applicationContext.getBeansOfType(ParameterDiffValidator.class)).hasSize(8);
    assertThat(applicationContext.getBeansOfType(PathItemDiffValidator.class)).hasSize(3);
    assertThat(applicationContext.getBeansOfType(RequestBodyDiffValidator.class)).hasSize(2);
    assertThat(applicationContext.getBeansOfType(ResponseDiffValidator.class)).hasSize(3);
    assertThat(applicationContext.getBeansOfType(SchemaDiffValidator.class)).hasSize(1);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
