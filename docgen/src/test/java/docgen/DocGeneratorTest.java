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

package docgen;

import io.swagger.models.Swagger;
import io.swagger.parser.Swagger20Parser;

import org.apache.commons.io.FileUtils;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.apache.servicecomb.toolkit.DocGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DocGeneratorTest {

  @Test
  public void contractTransferToSwaggerUI() throws IOException {

    Swagger20Parser swagger20Parser = new Swagger20Parser();

    InputStream in = DocGeneratorTest.class.getClassLoader().getResourceAsStream("HelloEndPoint.yaml");

    StringBuilder sb = new StringBuilder();
    byte[] bytes = new byte[1024];
    int len = -1;
    while ((len = in.read(bytes)) != -1) {
      sb.append(new String(bytes, 0, len));
    }

    Swagger swagger = swagger20Parser.parse(sb.toString());

    Path tempDir = Files.createTempDirectory(null);
    Path outputPath = Paths.get(tempDir.toFile().getAbsolutePath()
        + File.separator + "swagger-ui.html");
    DocGenerator docGenerator = GeneratorFactory.getGenerator(DocGenerator.class, "html");
    Map<String, Object> docGeneratorConfig = new HashMap<>();
    docGeneratorConfig.put("contractContent", swagger);
    docGeneratorConfig.put("outputPath", outputPath.toFile().getCanonicalPath());
    docGenerator.configure(docGeneratorConfig);
    docGenerator.generate();

    Assert.assertTrue(Files.exists(outputPath));
    FileUtils.deleteDirectory(tempDir.toFile());
  }

  @Test
  public void contractTransferToOther() {

    DocGenerator docGenerator = GeneratorFactory.getGenerator(DocGenerator.class, "other");
    Assert.assertNull(docGenerator);
  }
}
