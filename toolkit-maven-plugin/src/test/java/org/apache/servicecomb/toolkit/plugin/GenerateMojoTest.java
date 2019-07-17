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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.ClassMaker;
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
    File baseDir = this.resources.getBasedir(TEST_PROJECT_WITHCONTRACT);
    File baseDirWithoutContract = this.resources.getBasedir(TEST_PROJECT_WITHOUTCONTRACT);
    File contractLocation = this.resources.getBasedir(TEST_PROJECT_CONTRACTLOCATION);

    File pom = new File(baseDir, "pom.xml");
    AbstractMojo generateMojo = (AbstractMojo) this.rule.lookupMojo(PLUGIN_GOAL, pom);
    assertNotNull(generateMojo);

    String testDir = baseDir + File.separator;
    String testDirWithoutContract = baseDirWithoutContract + File.separator;
    String classesPath = "target/classes";

    final MavenProject project = mock(MavenProject.class);

    // code has no contract
    ClassMaker.compile(testDirWithoutContract);

    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(testDirWithoutContract + classesPath);

    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);
    rule.setVariableValueToObject(generateMojo, "project", project);

    try {
      rule.setVariableValueToObject(generateMojo, "sourceType", "code");
      rule.setVariableValueToObject(generateMojo, "outputDirectory", "./target");
      generateMojo.execute();

      assertEquals(0, Objects.requireNonNull(
          Paths.get(rule.getVariableValueFromObject(generateMojo, "contractLocation").toString()).toFile()
              .listFiles()).length);
    } catch (MojoFailureException e) {
      fail();
    }

    // code has contract
    ClassMaker.compile(testDir);

    runtimeUrlPath.remove(0);
    runtimeUrlPath.add(testDir + classesPath);
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);

    rule.setVariableValueToObject(generateMojo, "project", project);

    try {
      rule.setVariableValueToObject(generateMojo, "sourceType", "code");
      rule.setVariableValueToObject(generateMojo, "outputDirectory", "./target");
      rule.setVariableValueToObject(generateMojo, "contractFileType", "yaml");
      rule.setVariableValueToObject(generateMojo, "documentType", "html");
      generateMojo.execute();
    } catch (RuntimeException e) {
      fail();
    }

    try {
      rule.setVariableValueToObject(generateMojo, "sourceType", "code");
      generateMojo.execute();

      rule.setVariableValueToObject(generateMojo, "outputDirectory", null);
      generateMojo.execute();
    } catch (RuntimeException e) {
      assertEquals("output directory setting is invalid", e.getMessage());
    }

    try {
      rule.setVariableValueToObject(generateMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateMojo, "contractLocation", null);
      generateMojo.execute();
    } catch (RuntimeException e) {
      assertEquals("contract location is invalid or not set", e.getMessage());
    }

    try {
      rule.setVariableValueToObject(generateMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateMojo, "contractLocation", "");
      generateMojo.execute();

      rule.setVariableValueToObject(generateMojo, "contractLocation", "nonexists");
      generateMojo.execute();
    } catch (RuntimeException e) {
      assertThat(e.getMessage(), containsString("is not exists"));
    }
  }
}