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

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.servicecomb.toolkit.codegen.DefaultCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.airlift.airline.Command;
import io.airlift.airline.Option;
import io.swagger.codegen.config.CodegenConfigurator;

@Command(name = "codegenerate",
    description = "Generate multiple models of microservice project by OpenAPI specification file")
public class CodeGenerate implements Runnable {

  private final static Logger LOGGER = LoggerFactory.getLogger(CodeGenerate.class);

  @Option(name = {"-p", "--programming-model"}, title = "programming model", required = false,
      description = "programming model, as SpringMVC, POJO, JAX-RS, and SpringBoot")
  private String programmingModel;

  @Option(name = {"-m", "--microservice-framework"}, title = "framework",
      description = "microservice-framework")
  private String framework = "ServiceComb";

  @Option(name = {"-i", "--input"}, title = "OpenAPI specification file", required = true,
      description = "location of the OpenAPI specification file, as URL or file (required)")
  private String specFile;

  @Option(name = {"-o", "--output"}, title = "output directory",
      description = "location of the generated document (current dir by default)")
  private String output = "";

  @Option(name = {"--group-id"}, title = "group id",
      description = "groupId in generated microservice project")
  private String groupId;

  @Option(name = {"--artifact-id"}, title = "artifact id",
      description = "artifact id in generated microservice project")
  private String artifactId;

  @Option(name = {"--artifact-version"}, title = "artifact version",
      description = "artifact version in generated microservice project")
  private String artifactVersion;


  @Override
  public void run() {

    CodegenConfigurator configurator = new CodegenConfigurator();

    configurator.setOutputDir(output)
        .setGroupId(groupId)
        .setArtifactId(artifactId)
        .setArtifactVersion(artifactVersion)
        .setLibrary(programmingModel)
        .setLang(framework);

    if (isNotEmpty(specFile)) {

      File contractFile = new File(specFile);

      if (contractFile.isDirectory()) {
        try {
          Files.walkFileTree(Paths.get(contractFile.toURI()), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

              configurator.setInputSpec(file.toFile().getCanonicalPath())
                  .addAdditionalProperty("apiName", file.toFile().getName().split("\\.")[0]);

              try {
                new DefaultCodeGenerator().opts(configurator).generate();
              } catch (RuntimeException e) {
                throw new RuntimeException("Failed to generate code base on file " + file.toFile().getName());
              }

              return super.visitFile(file, attrs);
            }
          });
        } catch (RuntimeException | IOException e) {
          LOGGER.error(e.getMessage());
          return;
        }
      } else {
        configurator.setInputSpec(specFile);
        new DefaultCodeGenerator().opts(configurator).generate();
      }

      LOGGER.info("Success to generate code, the directory is: {}", output);
    }
  }
}
