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

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class FileUtilsTest {

  @Test
  public void createDirectoryTest() {
    Path path;
    try {
      path = Files.createTempDirectory("");
      FileUtils.createDirectory(path.toFile().getCanonicalPath());
    } catch (IOException e) {
      fail("Run 'createDirectoryTest' failed, unexpected to catch IOException: " + e.getMessage());
    }

    try {
      FileUtils.createDirectory(null);
      fail("Run 'createDirectoryTest' failed, expected to occur IOException but not");
    } catch (IOException e) {
      assertEquals("Path is null", e.getMessage());
    }
  }

  @Test
  public void getFilesGroupByFilenameTest() {

    try {
      FileUtils.getFilesGroupByFilename(null);
      fail("Run 'getFilesGroupByFilenameTest' failed, expected to occur IOException but not");
    } catch (IOException e) {
      assertEquals("Path is null", e.getMessage());
    }

    try {
      FileUtils.getFilesGroupByFilename("");
      fail("Run 'getFilesGroupByFilenameTest' failed, expected to occur IOException but not");
    } catch (IOException e) {
      assertThat(e.getMessage(), containsString("is not exists"));
    }
  }
}