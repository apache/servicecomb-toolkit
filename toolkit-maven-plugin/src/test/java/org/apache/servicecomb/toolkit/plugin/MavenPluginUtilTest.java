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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.apache.maven.project.MavenProject;
import org.junit.Test;

public class MavenPluginUtilTest {

  @Test
  public void isParentMavenProject() {

    MavenProject mavenProject = mock(MavenProject.class);
    given(mavenProject.getPackaging()).willReturn("pom");
    assertEquals(mavenProject.getPackaging(), "pom");
    MavenPluginUtil mavenPluginUtil = new MavenPluginUtil();
    assertTrue(mavenPluginUtil.isParentProject(mavenProject));
  }

  @Test
  public void notParentMavenProject() {

    MavenProject mavenProject = mock(MavenProject.class);
    given(mavenProject.getPackaging()).willReturn("jar");
    assertEquals(mavenProject.getPackaging(), "jar");
    MavenPluginUtil mavenPluginUtil = new MavenPluginUtil();
    assertFalse(mavenPluginUtil.isParentProject(mavenProject));
  }
}
