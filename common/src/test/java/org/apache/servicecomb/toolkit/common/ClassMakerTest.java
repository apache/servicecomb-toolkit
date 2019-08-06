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

package org.apache.servicecomb.toolkit.common;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;

public class ClassMakerTest {

  private static final String testResourceDirectory = "./src/test/resources";

  @Test
  public void runWithCorrectMavenProjectPath() throws InterruptedException, TimeoutException, IOException {

    String projectPath = testResourceDirectory + "/projects/demo-with-correct-pom/";
    ClassMaker.compile(projectPath);
    Assert.assertTrue(new File(projectPath + "/target").exists());
  }

  @Test
  public void runWithInvalidMavenProjectPath() throws InterruptedException, TimeoutException, IOException {

    String projectPath = testResourceDirectory + "/projects/demo-with-invalid-pom/";

    try {
      ClassMaker.compile(projectPath);
    } catch (RuntimeException e) {
      Assert.assertThat(e.getMessage(), StringContains.containsString("Command exec fail"));
      return;
    }
    Assert.fail();
  }
}
