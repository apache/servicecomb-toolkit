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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.ClassMaker;
import org.apache.servicecomb.toolkit.common.FileUtils;
import org.junit.Rule;
import org.junit.Test;


public class GenerateMojoTest {

  private static final String PLUGIN_GOAL = "generate";

  private static final String TEST_PROJECT_WITHCONTRACT = "demo-with-contract";

  private static final String TEST_PROJECT_WITHOUTCONTRACT = "demo-without-contract";

  static final String TEST_PROJECT_CONTRACTLOCATION = "contract";

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testGenerateMojo() throws Exception {
    File baseDirWithContract = this.resources.getBasedir(TEST_PROJECT_WITHCONTRACT);
    File baseDirWithoutContract = this.resources.getBasedir(TEST_PROJECT_WITHOUTCONTRACT);
    File contractLocation = this.resources.getBasedir(TEST_PROJECT_CONTRACTLOCATION);

    File pom = new File(baseDirWithContract, "pom.xml");
    AbstractMojo generateMojo = (AbstractMojo) this.rule.lookupMojo(PLUGIN_GOAL, pom);
    assertNotNull(generateMojo);

    String testDirWithContract = baseDirWithContract + File.separator;
    String testDirWithoutContract = baseDirWithoutContract + File.separator;
    String classesPath = "target/classes";

    final MavenProject project = mock(MavenProject.class);

    // code has no contract
    ClassMaker.compile(testDirWithoutContract);

    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(testDirWithoutContract + classesPath);
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);
    rule.setVariableValueToObject(generateMojo, "project", project);

    String outputDirectory = null;
    String contractOutput = null;
    String projectOutput = null;
    String documentOutput = null;

    try {
      outputDirectory = "./target";

      rule.setVariableValueToObject(generateMojo, "sourceType", "code");
      rule.setVariableValueToObject(generateMojo, "outputDirectory", outputDirectory);

      generateMojo.execute();

      assertEquals(0, Objects.requireNonNull(
          new File(rule.getVariableValueFromObject(generateMojo, "contractLocation").toString()).listFiles()).length);
    } catch (MojoFailureException e) {
      fail();
    }

    // code has contract
    ClassMaker.compile(testDirWithContract);

    runtimeUrlPath.remove(0);
    runtimeUrlPath.add(testDirWithContract + classesPath);
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);
    rule.setVariableValueToObject(generateMojo, "project", project);

    try {
      outputDirectory = "./target";
      contractOutput = outputDirectory + File.separator + "contract";
      projectOutput = outputDirectory + File.separator + "project";
      documentOutput = outputDirectory + File.separator + "document";

      rule.setVariableValueToObject(generateMojo, "sourceType", "code");
      rule.setVariableValueToObject(generateMojo, "outputDirectory", outputDirectory);
      rule.setVariableValueToObject(generateMojo, "contractFileType", "yaml");
      rule.setVariableValueToObject(generateMojo, "documentType", "html");
      rule.setVariableValueToObject(generateMojo, "service", new ServiceConfig());

      generateMojo.execute();

      assertNotEquals(0, Objects.requireNonNull(new File(outputDirectory).listFiles()).length);
      assertNotEquals(0, Objects.requireNonNull(new File(contractOutput).listFiles()).length);
      assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);
      assertNotEquals(0, Objects.requireNonNull(new File(documentOutput).listFiles()).length);
    } catch (RuntimeException e) {
      fail();
    }

    boolean isSuccessful;
    try {
      isSuccessful = false;

      rule.setVariableValueToObject(generateMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateMojo, "contractLocation", null);

      generateMojo.execute();
    } catch (RuntimeException e) {
      assertEquals("Invalid or not config contract location", e.getMessage());
      isSuccessful = true;
    }
    assertTrue(isSuccessful);

    try {
      isSuccessful = false;

      rule.setVariableValueToObject(generateMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateMojo, "contractLocation", "");

      generateMojo.execute();
    } catch (RuntimeException e) {
      assertThat(e.getMessage(), containsString("is not exists"));
      isSuccessful = true;
    }
    assertTrue(isSuccessful);

    outputDirectory = "./target";
    projectOutput = outputDirectory + File.separator + "project";
    ServiceConfig service = new ServiceConfig();

    rule.setVariableValueToObject(generateMojo, "sourceType", "code");
    rule.setVariableValueToObject(generateMojo, "outputDirectory", outputDirectory);
    FileUtils.createDirectory(projectOutput);
    rule.setVariableValueToObject(generateMojo, "service", null);
    generateMojo.execute();
    assertEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);

    rule.setVariableValueToObject(generateMojo, "sourceType", "code");
    rule.setVariableValueToObject(generateMojo, "outputDirectory", outputDirectory);
    rule.setVariableValueToObject(generateMojo, "service", service);
    generateMojo.execute();
    assertNotEquals(0, Objects.requireNonNull(new File(projectOutput).listFiles()).length);
  }
}