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

package org.apache.servicecomb.toolkit.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class CliTest {

  @Test
  public void generateServiceCombCodeFromSingleContract() throws IOException {

    String[] programModels = new String[] {"SpringMVC"};
    Path tempDir = Files.createTempDirectory(null);
    Arrays.stream(programModels).forEach(model -> {
      try {
        Path tempFile = Paths.get(tempDir.toFile().getCanonicalPath() + "/" + model + "Server");
        CliTest.class.getClassLoader().getResource("swagger.yaml");
        String[] args = new String[] {
            "generate",
            "-m",
            "ServiceCombProvider",
            "-i",
            Paths.get("./src/test/resources/swagger.yaml").toFile().getCanonicalPath(),
            "-o",
            tempFile.toFile().getCanonicalPath(),
            "-p",
            model
        };
        Assert.assertTrue(!Files.exists(tempFile));
        ToolkitMain.main(args);
        Assert.assertTrue(Files.exists(tempFile));
        Assert.assertTrue(Files.size(tempFile) > 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    tempDir.toFile().deleteOnExit();
  }
/*

  @Test
  public void generateSpringCloudCodeFromSingleContract() throws IOException {

    Path tempDir = Files.createTempDirectory(null);
    Path tempFile = Paths.get(tempDir.toFile().getCanonicalPath() + "/SpringCloudServer");
    String[] args = new String[] {
        "generate",
        "-l",
        "spring",
        "-i",
        Paths.get("./src/test/resources/swagger.yaml").toFile().getCanonicalPath(),
        "-o",
        tempFile.toFile().getCanonicalPath(),
        "--library",
        "spring-cloud"
    };
    Assert.assertTrue(!Files.exists(tempFile));

    ToolkitMain.main(args);
    Assert.assertTrue(Files.exists(tempFile));
    Assert.assertTrue(Files.size(tempFile) > 0);

    tempDir.toFile().deleteOnExit();
  }
*/

  @Test
  public void generateCodeFromMultiContract() throws IOException {

    Path tempDir = Files.createTempDirectory(null);
    Path tempFile = Paths.get(tempDir.toFile().getCanonicalPath() + "/ServiceCombProvider");
    String[] args = new String[] {
        "generate",
        "-i",
        Paths.get("./src/test/resources/contracts").toFile().getCanonicalPath(),
        "--artifact-id",
        "ServiceCombProvider",
        "--group-id",
        "org.apache.servicecomb.demo",
        "--artifact-version",
        "0.0.1",
        "--programming-model",
        "SpringMVC",
        "-o",
        tempFile.toFile().getCanonicalPath()
    };

    Assert.assertTrue(!Files.exists(tempFile));

    ToolkitMain.main(args);

    Assert.assertTrue(Files.exists(tempFile));
    Assert.assertTrue(Files.size(tempFile) > 0);

    tempDir.toFile().deleteOnExit();
  }
}
