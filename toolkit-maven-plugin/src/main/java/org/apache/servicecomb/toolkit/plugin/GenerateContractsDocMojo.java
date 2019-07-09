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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.swagger.SwaggerUtils;
import org.apache.servicecomb.toolkit.DocGenerator;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.apache.servicecomb.toolkit.common.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "generateDoc", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
@Execute(goal = "generateDoc",
    phase = LifecyclePhase.COMPILE
)
public class GenerateContractsDocMojo extends AbstractMojo {

  private static Logger LOGGER = LoggerFactory.getLogger(GenerateContractsDocMojo.class);

  @Parameter(defaultValue = "${project}")
  private MavenProject project;

  @Parameter(defaultValue = "code")
  private String sourceType;

  @Parameter(defaultValue = "contractLocation")
  private String contractLocation;

  @Parameter(defaultValue = "html")
  private String documentType;

  @Parameter(defaultValue = "documentOutput")
  private String documentOutput;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    try {

      switch (SourceType.valueOf(sourceType.toUpperCase())) {
        case CODE:
          File tmpFileDir = new File("target/tmp-contract-dir");
          if (!tmpFileDir.exists()) {
            if (!tmpFileDir.mkdirs()) {
              throw new MojoFailureException("fail to create directory to save contract");
            }
          }

          Path tmpPath = Files.createTempDirectory(Paths.get(tmpFileDir.toURI()), "");
          contractLocation = tmpPath.toFile().getCanonicalPath();
          LOGGER.debug("temporary contract location is " + contractLocation);

          ContractGenerator contractGenerator = new ContractGenerator(project);
          contractGenerator.generateAndOutput(contractLocation, "");
          if (Objects.requireNonNull(tmpPath.toFile().listFiles()).length == 0) {
            LOGGER.info("no contract in the code");
            return;
          }

          break;
        case CONTRACT:
          File file = new File(contractLocation);
          if (!file.exists()) {
            throw new MojoFailureException("contract location is not exists");
          }

          if (Objects.requireNonNull(file.listFiles()).length == 0) {
            throw new MojoFailureException(contractLocation + " has no contract files");
          }

          break;
        default:
          throw new MojoFailureException(sourceType + " is not supported now");
      }

      DocGenerator docGenerator = GeneratorFactory.getGenerator(DocGenerator.class, documentType);
      if (docGenerator == null) {
        throw new MojoFailureException("DocGenerator's implementation is not found");
      }

      Files.walkFileTree(Paths.get(contractLocation), new SimpleFileVisitor<Path>() {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

          Map<String, Object> docGeneratorConfig = new HashMap<>();
          docGeneratorConfig.put("contractContent", SwaggerUtils.parseSwagger(file.toUri().toURL()));
          docGeneratorConfig.put("outputPath", documentOutput + File.separator
              + file.toFile().getName().substring(0, file.toFile().getName().indexOf(".")));
          docGenerator.configure(docGeneratorConfig);
          if (!docGenerator.generate()) {
            throw new RuntimeException("Failed to generate doc base on file " + file.toFile().getName());
          }

          return super.visitFile(file, attrs);
        }
      });
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
