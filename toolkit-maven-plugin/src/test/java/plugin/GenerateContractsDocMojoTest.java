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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;


public class GenerateContractsDocMojoTest {

  private static final String PLUGIN_GOAL = "generateDoc";

  private static final String TEST_PROJECT = "project-generateContractsDoc";

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testGenerateContractsDoc() throws Exception {

    File baseDir = this.resources.getBasedir(TEST_PROJECT);

    File pom = new File(baseDir, "pom.xml");
    AbstractMojo generateContractsDocMojo = (AbstractMojo) this.rule.lookupMojo(PLUGIN_GOAL, pom);
    assertNotNull(generateContractsDocMojo);

    String testProjectDir = baseDir + File.separator;
    String testContractDir = testProjectDir + "contract";
    String testDocumentDir = testProjectDir + "document";

    if (!new File(testDocumentDir).exists()) {
      assertTrue((new File(testDocumentDir)).mkdir());
    }

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", testContractDir);
      rule.setVariableValueToObject(generateContractsDocMojo, "docOutput", testDocumentDir);
      rule.setVariableValueToObject(generateContractsDocMojo, "docType", "swagger-ui");
      generateContractsDocMojo.execute();
      assertNotEquals(0, Files.list(Paths.get(testDocumentDir)).count());
    } catch (MojoFailureException | IOException e) {
      fail();
    }

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", "");
      generateContractsDocMojo.execute();
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", "nonexitstdir");
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertEquals("contract location is not exists", e.getMessage());
    }

    String testEmptyDir = testProjectDir + "emptyDir";
    if (!new File(testEmptyDir).exists()) {
      assertTrue((new File(testEmptyDir)).mkdir());
    }
    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", testEmptyDir);
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertThat(e.getMessage(), containsString("has no contract files"));
    }

    try {
      rule.setVariableValueToObject(generateContractsDocMojo, "contractLocation", testDocumentDir);
      rule.setVariableValueToObject(generateContractsDocMojo, "docType", "nonImpl");
      generateContractsDocMojo.execute();
    } catch (MojoFailureException e) {
      assertEquals("DocGenerator's implementation is not found", e.getMessage());
    }
  }
}
