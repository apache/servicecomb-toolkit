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

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.swagger.SwaggerUtils;
import org.apache.servicecomb.toolkit.ContractsGenerator;
import org.apache.servicecomb.toolkit.DocGenerator;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateUtil {

  private static Logger LOGGER = LoggerFactory.getLogger(GenerateUtil.class);

  public static void generateContract(MavenProject project, String contractOutput, String contractFileType,
      String type) {

    Map<String, Object> contractConfig = new HashMap<>();
    try {
      contractConfig.put("classpathUrls", project.getRuntimeClasspathElements());
    } catch (DependencyResolutionRequiredException e) {
      throw new RuntimeException("failed to get runtime class elements", e);
    }
    contractConfig.put("outputDir", contractOutput);
    contractConfig.put("contractFileType", contractFileType);

    // TODO: support users to add other getGenerator type soon
    ContractsGenerator contractGenerator = GeneratorFactory.getGenerator(ContractsGenerator.class, type);
    Objects.requireNonNull(contractGenerator).configure(contractConfig);
    contractGenerator.generate();
  }

  public static void generateDocument(String contractLocation, String documentOutput, String type)
      throws IOException {

    // TODO: support users to add other getGenerator type soon
    DocGenerator docGenerator = GeneratorFactory.getGenerator(DocGenerator.class, type);
    if (docGenerator == null) {
      throw new RuntimeException("DocGenerator's implementation is not found");
    }

    Files.walkFileTree(Paths.get(contractLocation), new SimpleFileVisitor<Path>() {

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        Map<String, Object> docGeneratorConfig = new HashMap<>();

        docGeneratorConfig.put("contractContent", SwaggerUtils.parseSwagger(file.toUri().toURL()));
        docGeneratorConfig.put("outputPath", documentOutput + File.separator + file.toFile().getName()
            .substring(0, file.toFile().getName().indexOf(".")));

        docGenerator.configure(docGeneratorConfig);
        docGenerator.generate();

        return super.visitFile(file, attrs);
      }
    });
  }
}
