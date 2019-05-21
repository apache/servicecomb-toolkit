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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.docgen.DocGeneratorManager;
import org.apache.servicecomb.swagger.SwaggerUtils;

@Mojo(name = "generateDoc", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
@Execute(goal = "generateDoc",
    phase = LifecyclePhase.COMPILE
)
public class GenerateContractsDocMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "contracts")
  private String outputDir;

  @Parameter(defaultValue = ".yaml")
  private String format;

  @Parameter(defaultValue = "build/doc")
  private String docOutputDir;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    ContractGenerator contractGenerator = new ContractGenerator(project);
    contractGenerator.generateAndOutput(outputDir, format);

    try {

      Files.walkFileTree(Paths.get(outputDir), new SimpleFileVisitor<Path>() {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

          DocGeneratorManager.generate(SwaggerUtils.parseSwagger(file.toUri().toURL()),
              docOutputDir + File.separator
                  + file.toFile().getName().substring(0, file.toFile().getName().indexOf(".")) + ".html",
              "html");
          return super.visitFile(file, attrs);
        }
      });
    } catch (IOException e) {
      getLog().error(e.getMessage());
    }
  }
}
