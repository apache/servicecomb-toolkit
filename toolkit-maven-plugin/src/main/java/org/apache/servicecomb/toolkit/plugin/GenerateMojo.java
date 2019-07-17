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
import java.util.Objects;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.FileUtils;
import org.apache.servicecomb.toolkit.common.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
@Execute(goal = "generate", phase = LifecyclePhase.COMPILE)
public class GenerateMojo extends AbstractMojo {

  private static Logger LOGGER = LoggerFactory.getLogger(GenerateMojo.class);

  @Parameter(defaultValue = "${project}")
  private MavenProject project;

  @Parameter(defaultValue = "code")
  private String sourceType;

  @Parameter(defaultValue = "yaml")
  private String contractFileType;

  @Parameter(defaultValue = "html")
  private String documentType;

  @Parameter
  private String contractLocation;

  @Parameter(defaultValue = "./target")
  private String outputDirectory;

  @Override
  public void execute() {

    switch (SourceType.valueOf(sourceType.toUpperCase())) {
      case CODE:
        // generate contract file
        if (outputDirectory == null) {
          throw new RuntimeException("output directory setting is invalid");
        }
        String contractOutput = outputDirectory + File.separator + "contract";
        try {
          FileUtils.createDirectory(contractOutput);
        } catch (IOException e) {
          throw new RuntimeException("failed to generate contract.", e);
        }

        GenerateUtil.generateContract(project, contractOutput, contractFileType, "default");
        contractLocation = contractOutput;
        if (Objects.requireNonNull(new File(contractOutput).listFiles()).length == 0) {
          LOGGER.info("No contract in the code");
          return;
        }

        break;
      case CONTRACT:
        if (contractLocation == null) {
          throw new RuntimeException("contract location is invalid or not set");
        }

        if (!new File(contractLocation).exists()) {
          throw new RuntimeException("contract path " + contractLocation + " is not exists");
        }

        break;
      default:
        throw new RuntimeException("source type " + sourceType + " is not supported");
    }

    //generate document
    String documentOutput = outputDirectory + File.separator + "document";
    try {
      FileUtils.createDirectory(documentOutput);
      GenerateUtil.generateDocument(contractLocation, documentOutput, "default");
    } catch (IOException e) {
      throw new RuntimeException("failed to generate document", e);
    }
  }
}
