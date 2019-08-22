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

import static org.apache.servicecomb.toolkit.plugin.GenerateUtil.generateCode;
import static org.apache.servicecomb.toolkit.plugin.GenerateUtil.generateContract;
import static org.apache.servicecomb.toolkit.plugin.GenerateUtil.generateDocument;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GenerateUtilTest {

  @Rule
  public TestResources resources = new TestResources();

  private TestResourcesEx testResourcesEx;

  @Before
  public void setUp() throws Exception {
    this.testResourcesEx = new TestResourcesEx(resources);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testGenerateContract() throws DependencyResolutionRequiredException {

    MavenProject project = mock(MavenProject.class);

    String contractOutput = "target/GenerateUtilTest/contract";
    when(project.getRuntimeClasspathElements()).thenThrow(DependencyResolutionRequiredException.class);
    try {
      generateContract(project, contractOutput, "yaml", "default");

      fail("Run 'testGenerateContract' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Failed to get runtime class elements", e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testGenerateCode() throws Exception {

    String contractLocation = this.testResourcesEx.getContractLocation();

    String projectOutput = "target/GenerateUtilTest/project";
    ServiceConfig service = new ServiceConfig();
    generateCode(service, contractLocation, projectOutput, Collections.EMPTY_MAP, "default");
    assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);

    try {
      generateCode(service, contractLocation, projectOutput, Collections.EMPTY_MAP, "invalidType");

      fail("Run 'testGenerateCode' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Cannot found code generator's implementation", e.getMessage());
    }

    // Test the scenario where the configuration property contractLocation is specified as a file
    generateCode(service, contractLocation + File.separator + "HelloEndPoint.yaml", projectOutput,
        Collections.EMPTY_MAP, "default");
    assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);
  }

  @Test
  public void testGenerateDocument() throws Exception {

    String contractLocation = this.testResourcesEx.getContractLocation();

    String codeOutput = "target/GenerateUtilTest/document";
    generateDocument(contractLocation, codeOutput, "default");
    assertNotEquals(0, Objects.requireNonNull(new File(codeOutput).listFiles()).length);

    try {
      generateDocument(contractLocation, codeOutput, "invalidType");

      fail("Run 'testGenerateDocument' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertEquals("Cannot found document generator's implementation", e.getMessage());
    }
  }
}