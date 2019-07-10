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

package plugin;

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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.Rule;
import org.junit.Test;

import util.ClassMaker;


public class GenerateContractsDocMojoTest {

  private static final String PLUGIN_GOAL = "generateDoc";

  private static final String TEST_PROJECT_WITHCONTRACT = "demo-with-contract";

  private static final String TEST_PROJECT_NOCONTRACT = "demo-non-contract";

  private static final String TEST_PROJECT_CONTRACTLOCATION = "contract";

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testGenerateContractsDoc() throws Exception {

    File baseDir = this.resources.getBasedir(TEST_PROJECT_WITHCONTRACT);
    File baseDirNonContract = this.resources.getBasedir(TEST_PROJECT_NOCONTRACT);
    File contractLocation = this.resources.getBasedir(TEST_PROJECT_CONTRACTLOCATION);

    File pom = new File(baseDir, "pom.xml");
    AbstractMojo generateContractsDocMojo = (AbstractMojo) this.rule.lookupMojo(PLUGIN_GOAL, pom);
    assertNotNull(generateContractsDocMojo);

    String testDir = baseDir + File.separator;
    String testDirNonContract = baseDirNonContract + File.separator;
    String classesPath = "target/classes";
    String testDocumentDir = testDir + "document";
    String testContractDir = contractLocation.getCanonicalPath();

    if (!new File(testDocumentDir).exists()) {
      assertTrue((new File(testDocumentDir)).mkdirs());
    }

    final MavenProject project = mock(MavenProject.class);

    // code has no contract
    ClassMaker.compile(testDirNonContract);

    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(testDirNonContract + classesPath);

    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);
    rule.setVariableValueToObject(generateContractsDocMojo, "project", project);

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "sourceType", "code");
      generateContractsDocMojo.execute();

      assertEquals(0, Objects.requireNonNull(
          Paths.get(rule.getVariableValueFromObject(generateContractsDocMojo, "contractLocation").toString()).toFile()
              .listFiles()).length);
    } catch (MojoFailureException e) {
      fail();
    }

    // code has contract
    ClassMaker.compile(testDir);

    runtimeUrlPath.remove(0);
    runtimeUrlPath.add(testDir + classesPath);
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);
    rule.setVariableValueToObject(generateContractsDocMojo, "project", project);

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "sourceType", "code");
      rule.setVariableValueToObject(generateContractsDocMojo, "documentOutput", testDocumentDir);
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", "html");
      generateContractsDocMojo.execute();

      assertNotEquals(0, Files.list(Paths.get(testDocumentDir)).count());
    } catch (MojoFailureException | IOException e) {
      fail();
    }

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", "html");
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", "");
      generateContractsDocMojo.execute();

      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", "nonexitstdir");
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertEquals("contract location is not exists", e.getMessage());
    }

    String testEmptyDir = testDir + "emptyDir";
    if (!new File(testEmptyDir).exists()) {
      assertTrue((new File(testEmptyDir)).mkdirs());
    }
    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", "html");
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", testEmptyDir);
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertThat(e.getMessage(), containsString("has no contract files"));
    }

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", "html");
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", testContractDir);
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", "nonImpl");
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertEquals("DocGenerator's implementation is not found", e.getMessage());
    }

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "sourceType", "contract");
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", "html");
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", testContractDir);
      rule.setVariableValueToObject(generateContractsDocMojo, "documentType", testDocumentDir);
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertEquals("DocGenerator's implementation is not found", e.getMessage());
    }
  }
}
