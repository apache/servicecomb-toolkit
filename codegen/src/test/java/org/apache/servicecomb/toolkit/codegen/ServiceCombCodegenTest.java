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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenConfigLoader;
import io.swagger.codegen.SupportingFile;

public class ServiceCombCodegenTest {

  @Test
  public void testLoadImpl() {
    CodegenConfig codegenConfig = CodegenConfigLoader.forName("ServiceComb");
    Assert.assertEquals(ServiceCombCodegen.class, codegenConfig.getClass());
  }

  @Test
  public void providerDirectoryStrategy() {

    DirectoryStrategy providerDirectoryStrategy = new ProviderDirectoryStrategy();

    Map<String, Object> propertiesMap = new HashMap<>();
    propertiesMap.put("artifactId", "provider");
    propertiesMap.put("mainClassPackage", "provider");
    providerDirectoryStrategy.addCustomProperties(propertiesMap);
    providerDirectoryStrategy.processSupportingFile(new ArrayList<SupportingFile>());
    Assert.assertEquals("provider", providerDirectoryStrategy.providerDirectory());
    Assert.assertEquals("provider", providerDirectoryStrategy.modelDirectory());

    try {
      providerDirectoryStrategy.consumerDirectory();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof UnsupportedOperationException);
    }
  }

  @Test
  public void consumerDirectoryStrategy() {

    DirectoryStrategy consumerDirectoryStrategy = new ConsumerDirectoryStrategy();

    Map<String, Object> propertiesMap = new HashMap<>();
    propertiesMap.put("artifactId", "consumer");
    propertiesMap.put("mainClassPackage", "consumer");
    propertiesMap.put("apiTemplateFiles", new HashMap<String, String>());
    consumerDirectoryStrategy.addCustomProperties(propertiesMap);
    consumerDirectoryStrategy.processSupportingFile(new ArrayList<SupportingFile>());
    Assert.assertEquals("consumer", consumerDirectoryStrategy.consumerDirectory());
    Assert.assertEquals("consumer", consumerDirectoryStrategy.modelDirectory());

    try {
      consumerDirectoryStrategy.providerDirectory();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof UnsupportedOperationException);
    }
  }

  @Test
  public void defaultDirectoryStrategy() {

    DirectoryStrategy defaultDirectoryStrategy = new DefaultDirectoryStrategy();

    Map<String, Object> propertiesMap = new HashMap<>();
    propertiesMap.put("artifactId", "all");
    propertiesMap.put("mainClassPackage", "all");
    propertiesMap.put("apiTemplateFiles", new HashMap<String, String>());
    defaultDirectoryStrategy.addCustomProperties(propertiesMap);
    defaultDirectoryStrategy.processSupportingFile(new ArrayList<SupportingFile>());
    Assert.assertEquals("consumer", defaultDirectoryStrategy.consumerDirectory());
    Assert.assertEquals("provider", defaultDirectoryStrategy.providerDirectory());
    Assert.assertEquals("model", defaultDirectoryStrategy.modelDirectory());

  }
}
