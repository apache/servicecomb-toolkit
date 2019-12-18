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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class CliTest {
  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  public void testGenerateServiceCombCodeFromSingleContract() throws IOException {

    String[] programModels = new String[] {"SpringMVC", "POJO", "JAX-RS", "SpringBoot"};
    Path tempDir = Files.createTempDirectory(null);
    Arrays.stream(programModels).forEach(model -> {
      try {
        Path tempFile = Paths.get(tempDir.toFile().getCanonicalPath() + "/" + model + "Server");
        CliTest.class.getClassLoader().getResource("swagger.yaml");
        String[] args = new String[] {
            "codegenerate",
            "-m",
            "ServiceComb",
            "-i",
            Paths.get("./src/test/resources/swagger.yaml").toFile().getCanonicalPath(),
            "-o",
            tempFile.toFile().getCanonicalPath(),
            "-p",
            model,
            "--properties",
            "prop1=value,prop2=value"
        };
        Assert.assertTrue(!Files.exists(tempFile));
        ToolkitMain.main(args);
        Assert.assertTrue(Files.exists(tempFile));
        Assert.assertTrue(FileUtils.sizeOfDirectory(tempFile.toFile()) > 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    tempDir.toFile().deleteOnExit();
  }

  @Test
  public void testGenerateCodeFromMultiContract() throws IOException {

    Path tempDir = Files.createTempDirectory(null);
    Path tempFile = Paths.get(tempDir.toFile().getCanonicalPath() + "/ServiceComb");
    String[] args = new String[] {
        "codegenerate",
        "-i",
        Paths.get("./src/test/resources/contracts").toFile().getCanonicalPath(),
        "--artifact-id",
        "ServiceComb",
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
    Assert.assertTrue(FileUtils.sizeOfDirectory(tempFile.toFile()) > 0);

    tempDir.toFile().deleteOnExit();
  }

  @Test
  public void testCheckStyle() throws IOException {
    exit.expectSystemExitWithStatus(1);
    String[] args = new String[] {
        "checkstyle",
        "-r",
        Paths.get("./src/test/resources/oas/style-rules.properties").toFile().getCanonicalPath(),
        "-f",
        Paths.get("./src/test/resources/oas/style.yaml").toFile().getCanonicalPath()
    };
    ToolkitMain.main(args);
  }

  @Test
  public void testCheckStyleAbbr() throws IOException {
    exit.expectSystemExitWithStatus(1);
    String[] args = new String[] {
        "cs",
        "-r",
        Paths.get("./src/test/resources/oas/style-rules.properties").toFile().getCanonicalPath(),
        "-f",
        Paths.get("./src/test/resources/oas/style.yaml").toFile().getCanonicalPath()
    };
    ToolkitMain.main(args);
  }

  @Test
  public void testCheckCompatibility() throws IOException {
    exit.expectSystemExitWithStatus(1);
    String[] args = new String[] {
        "checkcompatibility",
        Paths.get("./src/test/resources/oas/compatibility-left.yaml").toFile().getCanonicalPath(),
        Paths.get("./src/test/resources/oas/compatibility-right.yaml").toFile().getCanonicalPath()
    };
    ToolkitMain.main(args);
  }

  @Test
  public void testCheckCompatibilityAbbr() throws IOException {
    exit.expectSystemExitWithStatus(1);
    String[] args = new String[] {
        "cc",
        Paths.get("./src/test/resources/oas/compatibility-left.yaml").toFile().getCanonicalPath(),
        Paths.get("./src/test/resources/oas/compatibility-right.yaml").toFile().getCanonicalPath()
    };
    ToolkitMain.main(args);
  }
}
