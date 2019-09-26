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

package org.apache.servicecomb.toolkit.codegen;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.CodegenConfigLoader;

public class ServiceCombCodegenTest {

  @Test
  public void testLoadImpl() {
    CodegenConfig codegenConfig = CodegenConfigLoader.forName("ServiceComb");
    Assert.assertEquals(ServiceCombCodegen.class, codegenConfig.getClass());
  }

  @Test
  public void defaultValue(){
    CodegenConfig codegenConfig = CodegenConfigLoader.forName("ServiceComb");
    Assert.assertEquals(ServiceCombCodegen.class, codegenConfig.getClass());
    ServiceCombCodegen serviceCombCodegen = (ServiceCombCodegen)codegenConfig;
    serviceCombCodegen.processOpts();

    Map<String, Object> additionalProperties = serviceCombCodegen.additionalProperties();
    Assert.assertEquals("domain.orgnization.project.sample",additionalProperties.get("mainClassPackage"));
    Assert.assertEquals("domain.orgnization.project.sample.api",additionalProperties.get("apiPackage"));
    Assert.assertEquals("domain.orgnization.project.sample.model",additionalProperties.get("modelPackage"));

  }
}
