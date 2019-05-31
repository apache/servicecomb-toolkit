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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.plugin.GenerateContractsDocMojo;
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
    GenerateContractsDocMojo generateContractsDocMojo = mock(GenerateContractsDocMojo.class);
    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(baseDir + "/target/classes");
    final MavenProject project = mock(MavenProject.class);
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);

    assertNotNull(generateContractsDocMojo);
    rule.setVariableValueToObject(generateContractsDocMojo, "project", project);
    rule.setVariableValueToObject(generateContractsDocMojo, "format", ".yaml");
    assertNotNull(this.rule.getVariableValueFromObject(generateContractsDocMojo, "project"));
    assertEquals(".yaml", this.rule.getVariableValueFromObject(generateContractsDocMojo, "format"));
    rule.executeMojo(project, PLUGIN_GOAL);
    generateContractsDocMojo.execute();
  }
}
