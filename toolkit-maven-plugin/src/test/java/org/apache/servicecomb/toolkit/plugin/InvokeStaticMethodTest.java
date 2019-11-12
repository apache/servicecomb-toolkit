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

package org.apache.servicecomb.toolkit.plugin;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtils.class, GenerateUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class InvokeStaticMethodTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  private TestResourcesEx testGenerateMojoResources;

  private TestResourcesEx testVerifyMojoResources;

  @Before
  public void setUp() throws Exception {

    testGenerateMojoResources = new TestResourcesEx(resources);
    testVerifyMojoResources = new TestResourcesEx(resources);

    testGenerateMojoResources.createMojo(rule, "generate");
    testVerifyMojoResources.createMojo(rule, "verify");

    MavenProject project = mock(MavenProject.class);
    given(project.getBasedir()).willReturn(new File("mockProject"));
    testGenerateMojoResources.setVariableValueToObject("project", project);
    testVerifyMojoResources.setVariableValueToObject("project", project);
  }

  @Test
  public void testVerifyMojoInvokeGenerateUtilGenerateContract()
      throws MojoFailureException, MojoExecutionException, IllegalAccessException {

    PowerMockito.mockStatic(GenerateUtil.class);
    PowerMockito.doThrow(new RuntimeException()).when(GenerateUtil.class);
    // Powermockito limit: use argument matchers to specify method which would be mock
    GenerateUtil.generateContract(anyObject(), anyString(), anyString(), anyString());

    testVerifyMojoResources.setVariableValueToObject("sourceType", "code");
    try {
      testVerifyMojoResources.execute();

      fail("Run 'testVerifyMojoInvokeGenerateUtilGenerateContract' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate contract from code", e.getMessage());
      assertThat(e.getCause().toString(), containsString("RuntimeException"));
    }
  }

  @Test
  public void testVerifyMojoInvokeFileUtilsCreateTempDirectory()
      throws MojoFailureException, MojoExecutionException, IllegalAccessException, IOException {

    PowerMockito.mockStatic(FileUtils.class);
    PowerMockito.doThrow(new IOException()).when(FileUtils.class);
    // Powermockito limit: use argument matchers to specify method which would be mock
    FileUtils.createTempDirectory(anyString());

    testVerifyMojoResources.setVariableValueToObject("sourceType", "code");
    try {
      testVerifyMojoResources.execute();

      fail("Run 'testVerifyMojoInvokeFileUtilsCreateTempDirectory' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate contract from code", e.getMessage());
      assertThat(e.getCause().toString(), containsString("IOException"));
    }
  }

  @Test
  public void testGenerateMojoInvokeFileUtilsCreateDirectory()
      throws IllegalAccessException, MojoFailureException, MojoExecutionException, IOException {

    PowerMockito.mockStatic(FileUtils.class);
    PowerMockito.doThrow(new IOException()).when(FileUtils.class);
    // Powermockito limit: use argument matchers to specify method which would be mock
    FileUtils.createDirectory(anyString());

    testGenerateMojoResources.setVariableValueToObject("sourceType", "code");
    try {
      testGenerateMojoResources.execute();

      fail("Run 'testGenerateMojoInvokeFileUtilsCreateDirectory' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate contract", e.getMessage());
      assertThat(e.getCause().toString(), containsString("IOException"));
    }
  }

  @Test
  public void testGenerateMojoInvokeGenerateUtilGenerateCode()
      throws IllegalAccessException, MojoFailureException, MojoExecutionException, IOException {

    PowerMockito.mockStatic(GenerateUtil.class);
    PowerMockito.doThrow(new IOException()).when(GenerateUtil.class);
    // Powermockito limit: use argument matchers to specify method which would be mock
    GenerateUtil.generateCode(anyObject(), anyString(), anyString(), any(), anyString());

    testGenerateMojoResources.setVariableValueToObject("sourceType", "contract");
    testGenerateMojoResources.setVariableValueToObject("outputDirectory", "target/InvokeStaticMethodTest");
    testGenerateMojoResources
        .setVariableValueToObject("contractLocation", testGenerateMojoResources.getContractLocation());
    testGenerateMojoResources.setVariableValueToObject("service", new ServiceConfig());
    try {
      testGenerateMojoResources.execute();

      fail("Run 'testGenerateMojoInvokeGenerateUtilGenerateCode' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate code", e.getMessage());
      assertThat(e.getCause().toString(), containsString("IOException"));
    }

    PowerMockito.doThrow(new RuntimeException()).when(GenerateUtil.class);
    GenerateUtil.generateCode(anyObject(), anyString(), anyString(), any(), anyString());
    try {
      testGenerateMojoResources.execute();

      fail("Run 'testGenerateMojoInvokeGenerateUtilGenerateCode' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate code", e.getMessage());
      assertThat(e.getCause().toString(), containsString("RuntimeException"));
    }
  }

  @Test
  public void testGenerateMojoInvokeGenerateUtilGenerateDocument()
      throws IllegalAccessException, MojoFailureException, MojoExecutionException, IOException {

    PowerMockito.mockStatic(GenerateUtil.class);
    PowerMockito.doThrow(new IOException()).when(GenerateUtil.class);
    // Powermockito limit: use argument matchers to specify method which would be mock
    GenerateUtil.generateDocument(anyString(), anyString(), anyString());

    testGenerateMojoResources.setVariableValueToObject("sourceType", "contract");
    testGenerateMojoResources.setVariableValueToObject("outputDirectory", "target/InvokeStaticMethodTest");
    testGenerateMojoResources
        .setVariableValueToObject("contractLocation", testGenerateMojoResources.getContractLocation());
    testGenerateMojoResources.setVariableValueToObject("service", new ServiceConfig());
    try {
      testGenerateMojoResources.execute();

      fail(
          "Run 'testGenerateMojoInvokeGenerateUtilGenerateDocument' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate document", e.getMessage());
      assertThat(e.getCause().toString(), containsString("IOException"));
    }

    PowerMockito.doThrow(new RuntimeException()).when(GenerateUtil.class);
    GenerateUtil.generateDocument(anyString(), anyString(), anyString());
    try {
      testGenerateMojoResources.execute();

      fail(
          "Run 'testGenerateMojoInvokeGenerateUtilGenerateDocument' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to generate document", e.getMessage());
      assertThat(e.getCause().toString(), containsString("RuntimeException"));
    }
  }
}
