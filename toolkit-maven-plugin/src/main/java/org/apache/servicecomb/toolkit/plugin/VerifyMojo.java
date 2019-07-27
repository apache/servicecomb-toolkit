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

import java.io.IOException;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.ContractComparator;
import org.apache.servicecomb.toolkit.common.FileUtils;
import org.apache.servicecomb.toolkit.common.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "verify", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
@Execute(goal = "verify", phase = LifecyclePhase.COMPILE)
public class VerifyMojo extends AbstractMojo {

  private final static Logger LOGGER = LoggerFactory.getLogger(VerifyMojo.class);

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "code")
  private String sourceType;

  @Parameter
  private String sourceContractPath;

  @Parameter
  private String destinationContractPath;


  @Override
  public void execute() {

    String contractFileType = "yaml";

    switch (SourceType.valueOf(sourceType.toUpperCase())) {
      case CODE:
        try {
          sourceContractPath = FileUtils.createTempDirectory("target/tmp-contract").toFile().getCanonicalPath();
          GenerateUtil.generateContract(project, sourceContractPath, contractFileType, "default");
        } catch (RuntimeException | IOException e) {
          throw new RuntimeException("Failed to generate contract from code", e);
        }

        break;
      case CONTRACT:

        break;
      default:
        throw new RuntimeException("Not support source type " + sourceType);
    }

    try {
      Map<String, byte[]> sourceContractGroup = FileUtils.getFilesGroupByFilename(sourceContractPath);
      Map<String, byte[]> destinationContractGroup = FileUtils.getFilesGroupByFilename(destinationContractPath);

      sourceContractGroup.forEach((contractName, swagger) -> {

        byte[] sourceSwagger = destinationContractGroup.get(contractName);

        ContractComparator contractComparator = new ContractComparator(new String(sourceSwagger), new String(swagger));

        if (!contractComparator.equals()) {
          LOGGER.info("Contract is not matched, difference is as follows");
          LOGGER.info(destinationContractPath + "/" + contractName + " vs " + sourceContractPath + "/" + contractName);
          contractComparator.splitPrintToScreen();
        } else {
          LOGGER.info("Succee, contract verification passed");
        }
      });
    } catch (IOException e) {
      throw new RuntimeException("Failed to verify contract ", e);
    }
  }
}
