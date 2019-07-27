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
import java.io.IOException;
import java.util.Objects;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.Rule;
import org.junit.Test;

public class GenerateUtilTest {

  private static final String TEST_PROJECT_CONTRACTLOCATION = "contract";

  @Rule
  public TestResources resources = new TestResources();

  public GenerateUtilTest() throws IOException {
  }


  @Test
  public void testGenerateContract() throws DependencyResolutionRequiredException {

    MavenProject project = mock(MavenProject.class);

    String contractOutput = "target/contract";
    when(project.getRuntimeClasspathElements()).thenThrow(DependencyResolutionRequiredException.class);
    try {
      generateContract(project, contractOutput, "yaml", "default");
    } catch (RuntimeException e) {
      assertEquals("Failed to get runtime class elements", e.getMessage());
      return;
    }

    fail("Run 'testGenerateContract' failed, expected to catch RuntimeException but not");
  }

  @Test
  public void testGenerateCode() throws IOException {
    File contractLocation = resources.getBasedir(TEST_PROJECT_CONTRACTLOCATION);

    String projectOutput = "./target/project";
    ServiceConfig service = new ServiceConfig();
    generateCode(service, contractLocation.getCanonicalPath(), projectOutput, "default");
    assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);

    try {
      generateCode(service, contractLocation.getCanonicalPath(), projectOutput, "invalidType");
    } catch (RuntimeException e) {
      assertEquals("Cannot found code generator's implementation", e.getMessage());
      return;
    }

    fail("Run 'testGenerateCode' failed, expected to catch RuntimeException but not");
  }

  @Test
  public void testGenerateDocument() throws IOException {
    File contractLocation = resources.getBasedir(TEST_PROJECT_CONTRACTLOCATION);

    String codeOutput = "./target/document";
    generateDocument(contractLocation.getCanonicalPath(), codeOutput, "default");
    assertNotEquals(0, Objects.requireNonNull(new File(codeOutput).listFiles()).length);

    try {
      generateDocument(contractLocation.getCanonicalPath(), codeOutput, "invalidType");
    } catch (RuntimeException e) {
      assertEquals("Cannot found document generator's implementation", e.getMessage());
      return;
    }

    fail("Run 'testGenerateDocument' failed, expected to catch RuntimeException but not");
  }
}