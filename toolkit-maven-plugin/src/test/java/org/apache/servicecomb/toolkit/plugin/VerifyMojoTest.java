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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.Rule;
import org.junit.Test;

public class VerifyMojoTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testVerifyMojo() throws Exception {

    TestResourcesEx testResourcesEx = new TestResourcesEx(resources);

    String testDirWithContract = testResourcesEx.getBasedirWithContract();

    testResourcesEx.createMojo(rule, "verify");

    MavenProject project = mock(MavenProject.class);
    testResourcesEx.setVariableValueToObject("project", project);

    try {
      given(project.getRuntimeClasspathElements()).willReturn(testResourcesEx.getRuntimeUrlPath(testDirWithContract));
      testResourcesEx.setVariableValueToObject("sourceType", "code");
      testResourcesEx.setVariableValueToObject("destinationContractPath", testResourcesEx.getContractDestination());
      testResourcesEx.execute();
    } catch (RuntimeException e) {
      fail("Run 'testVerifyMojo' failed, unexpected to catch RuntimeException: " + e.getMessage());
    }

    try {
      testResourcesEx.setVariableValueToObject("sourceType", "contract");
      testResourcesEx.setVariableValueToObject("sourceContractPath", null);

      testResourcesEx.execute();

      fail("Run 'testVerifyMojo' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertThat(e.getMessage(), containsString("Failed to verify contract"));
    }

    try {
      testResourcesEx.setVariableValueToObject("sourceType", "contract");
      testResourcesEx.setVariableValueToObject("sourceContractPath", testResourcesEx.getContractLocation());
      testResourcesEx.setVariableValueToObject("destinationContractPath", null);

      testResourcesEx.execute();

      fail("Run 'testVerifyMojo' failed, expected to occur RuntimeException but not");
    } catch (RuntimeException e) {
      assertThat(e.getMessage(), containsString("Failed to verify contract"));
    }

    try {
      testResourcesEx.setVariableValueToObject("sourceType", "contract");
      testResourcesEx.setVariableValueToObject("sourceContractPath", testResourcesEx.getContractLocation());
      testResourcesEx.setVariableValueToObject("destinationContractPath", testResourcesEx.getContractDestination());
      testResourcesEx.execute();
    } catch (RuntimeException e) {
      fail("Run 'testVerifyMojo' failed, unexpected to catch RuntimeException: " + e.getMessage());
    }
  }
}