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

import io.airlift.airline.Command;
import io.airlift.airline.Option;
import io.swagger.codegen.config.CodegenConfigurator;

@Command(name = "generate", description = "CodeGenerate code with chosen lang")
public class CodeGenerate implements Runnable {

  @Option(name = {"--programming-model"}, title = "programming model", required = false,
      description = "programming model, equals to --library")
  private String programmingModel;

  @Option(name = {"-i", "--input-spec"}, title = "spec file", required = true,
      description = "location of the swagger spec, as URL or file (required)")
  private String spec;

  @Option(name = {"-o", "--output"}, title = "output directory",
      description = "where to write the generated files (current dir by default)")
  private String output = "";

  @Option(name = {"--group-id"}, title = "group id", description = "groupId in generated pom.xml")
  private String groupId;

  @Option(name = {"--artifact-id"}, title = "artifact id",
      description = "artifact version in generated pom.xml")
  private String artifactId;

  @Option(name = {"--artifact-version"}, title = "artifact version",
      description = "artifact version in generated pom.xml")
  private String artifactVersion;

  @Option(name = {"--library"}, title = "library", description = "library template (sub-template)")
  private String library;

  @Option(name = {"-l", "--lang"}, title = "language",
      description = "client language to generate (maybe class name in classpath, required)")
  private String lang = "ServiceCombProvider";


  @Override
  public void run() {

    CodegenConfigurator configurator = new CodegenConfigurator();

    configurator.setOutputDir(output)
        .setGroupId(groupId)
        .setArtifactId(artifactId)
        .setArtifactVersion(artifactVersion)
        .setLibrary(library)
        .setLibrary(programmingModel)
        .setLang(lang);

    if (isNotEmpty(spec)) {

      File contractFile = new File(spec);

      // has many contracts
      if (contractFile.isDirectory()) {

        try {
          Files.walkFileTree(Paths.get(contractFile.toURI()), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

              configurator.setInputSpec(file.toFile().getCanonicalPath())
                  .addAdditionalProperty("apiName", file.toFile().getName().split("\\.")[0]);

              new DefaultCodeGenerator().opts(configurator).generate();

              return super.visitFile(file, attrs);
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        // one contract
        configurator.setInputSpec(spec);
        new DefaultCodeGenerator().opts(configurator).generate();
      }
    }
  }
}
