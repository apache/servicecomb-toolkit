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
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.apache.servicecomb.toolkit.CodeGenerator;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.apache.servicecomb.toolkit.codegen.ProjectMetaConstant;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.airlift.airline.Command;
import io.airlift.airline.Option;

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

  @Option(name = {"--api-package"}, title = "api package",
      description = "api package in generated microservice project")
  private String apiPackage;

  @Option(name = {"--model-package"}, title = "model package",
      description = "model package in generated microservice project")
  private String modelPackage;

  @Option(name = {"-t", "--service-type"}, title = "service type",
      description = "microservice type of generated microservice project. optional value is provider,consumer,all")
  private String serviceType = "all";

  @Option(
      name = {"--properties"},
      title = "additional properties",
      description =
          "usage: --properties name=value,name=value. These Properties can be referenced by the mustache templates."
              + " You can specify one or more value")
  private String properties;

  @Override
  public void run() {

    CodeGenerator codegenerator = GeneratorFactory.getGenerator(CodeGenerator.class, "default");

    if (codegenerator == null) {
      LOGGER.warn("Not CodeGenerator found");
      return;
    }

    CodegenConfigurator configurator = new CodegenConfigurator();

    // add additional property
    Optional.ofNullable(properties).ifPresent(properties ->
        Arrays.stream(properties.split(",")).forEach(property -> {
          String[] split = property.split("=");
          if (split != null && split.length == 2) {
            configurator.addAdditionalProperty(split[0], split[1]);
          }
        })
    );

    configurator.setOutputDir(output)
        .setGroupId(groupId)
        .setArtifactId(artifactId)
        .setArtifactVersion(artifactVersion)
        .setLibrary(programmingModel)
        .setGeneratorName(framework)
        .setApiPackage(apiPackage)
        .setModelPackage(modelPackage);

    configurator.addAdditionalProperty(ProjectMetaConstant.SERVICE_TYPE, serviceType);

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
                codegenerator.configure(Collections.singletonMap("configurator", configurator));
                codegenerator.generate();
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
        configurator.setInputSpec(specFile).addAdditionalProperty("apiName", contractFile.getName().split("\\.")[0]);
        codegenerator.configure(Collections.singletonMap("configurator", configurator));
        codegenerator.generate();
      }

      LOGGER.info("Success to generate code, the directory is: {}", output);
    }
  }
}
