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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.Objects;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.FileUtils;
import org.junit.Rule;
import org.junit.Test;


public class GenerateMojoTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testGenerateMojo() throws Exception {

    TestResourcesEx testResourcesEx = new TestResourcesEx(resources);

    String testDirWithContract = testResourcesEx.getBasedirWithContract();
    String testDirWithoutContract = testResourcesEx.getBasedirWithoutContract();

    String outputDirectory = null;
    String contractOutput = null;
    String projectOutput = null;
    String documentOutput = null;

    final MavenProject project = mock(MavenProject.class);
    given(project.getBasedir()).willReturn(new File("mockProject"));

    // code has no contract
    testResourcesEx.createMojo(rule, "generate");
    testResourcesEx.setVariableValueToObject("project", project);
    given(project.getRuntimeClasspathElements()).willReturn(testResourcesEx.getRuntimeUrlPath(testDirWithoutContract));
    try {
      outputDirectory = "target/GenerateMojoTest";

      FileUtils.deleteDirectory(outputDirectory);
      testResourcesEx.setVariableValueToObject("sourceType", "code");
      testResourcesEx.setVariableValueToObject("outputDirectory", outputDirectory);

      testResourcesEx.execute();

      assertFalse(new File(testResourcesEx.getVariableValueFromObject("contractLocation")).listFiles().length != 0);
    } catch (MojoFailureException e) {
      fail("Run 'testGenerateMojo' failed, unexpected to catch MojoFailureException: " + e.getMessage());
    }

    // code has contract
    testResourcesEx.createMojo(rule, "generate");
    testResourcesEx.setVariableValueToObject("project", project);
    given(project.getRuntimeClasspathElements()).willReturn(testResourcesEx.getRuntimeUrlPath(testDirWithContract));
    try {
      outputDirectory = "target/GenerateMojoTest";
      contractOutput = outputDirectory + File.separator + "contract";
      projectOutput = outputDirectory + File.separator + "project";
      documentOutput = outputDirectory + File.separator + "document";

      testResourcesEx.setVariableValueToObject("sourceType", "code");
      testResourcesEx.setVariableValueToObject("outputDirectory", outputDirectory);
      testResourcesEx.setVariableValueToObject("contractFileType", "yaml");
      testResourcesEx.setVariableValueToObject("documentType", "html");
      testResourcesEx.setVariableValueToObject("service", new ServiceConfig());

      testResourcesEx.execute();

      assertNotEquals(0, Objects.requireNonNull(new File(contractOutput).listFiles()).length);
      assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);
      assertNotEquals(0, Objects.requireNonNull(new File(documentOutput).listFiles()).length);
    } catch (RuntimeException e) {
      fail("Run 'testGenerateMojo' failed, unexpected to catch RuntimeException: " + e.getMessage());
    }

    try {
      testResourcesEx.setVariableValueToObject("sourceType", "contract");
      testResourcesEx.setVariableValueToObject("contractLocation", null);

      testResourcesEx.execute();

      fail("Run 'testGenerateMojo' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Invalid or not config contract location", e.getMessage());
    }

    try {
      testResourcesEx.setVariableValueToObject("sourceType", "contract");
      testResourcesEx.setVariableValueToObject("contractLocation", "");

      testResourcesEx.execute();

      fail("Run 'testGenerateMojo' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertThat(e.getMessage(), containsString("is not exists"));
    }

    try {
      outputDirectory = "target/GenerateMojoTest";
      projectOutput = outputDirectory + File.separator + "project";
      documentOutput = outputDirectory + File.separator + "document";

      testResourcesEx.setVariableValueToObject("sourceType", "contract");
      testResourcesEx.setVariableValueToObject("contractLocation", testResourcesEx.getContractLocation());
      testResourcesEx.setVariableValueToObject("outputDirectory", outputDirectory);
      testResourcesEx.setVariableValueToObject("service", new ServiceConfig());

      testResourcesEx.execute();

      assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);
      assertNotEquals(0, Objects.requireNonNull(new File(documentOutput).listFiles()).length);
    } catch (RuntimeException e) {
      fail("Run 'testGenerateMojo' failed, unexpected to catch RuntimeException: " + e.getMessage());
    }

    outputDirectory = "target/GenerateMojoTest";
    projectOutput = outputDirectory + File.separator + "project";
    ServiceConfig service = new ServiceConfig();

    testResourcesEx.setVariableValueToObject("sourceType", "code");
    testResourcesEx.setVariableValueToObject("outputDirectory", outputDirectory);
    FileUtils.deleteDirectory(projectOutput);
    FileUtils.createDirectory(projectOutput);
    testResourcesEx.setVariableValueToObject("service", null);
    testResourcesEx.execute();
    assertEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);

    testResourcesEx.setVariableValueToObject("sourceType", "code");
    testResourcesEx.setVariableValueToObject("outputDirectory", outputDirectory);
    testResourcesEx.setVariableValueToObject("service", service);
    testResourcesEx.execute();
    assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);
  }
}