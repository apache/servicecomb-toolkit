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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.ContractComparator;
import org.apache.servicecomb.toolkit.common.ContractsUtils;

@Mojo(name = "verifyContracts", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
@Execute(goal = "verifyContracts",
    phase = LifecyclePhase.COMPILE
)
public class VerifyContractsMojo extends AbstractMojo {


  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "contracts")
  private String outputDir;

  @Parameter(defaultValue = ".yaml")
  private String format;

  @Parameter(defaultValue = "sourceContracts")
  private String sourceContractsDir;


  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    getLog().info("outputDir : " + outputDir);

    ContractGenerator contractGenerator = new ContractGenerator(project);

    contractGenerator.generateAndOutput(outputDir, format);

    try {

      Map<String, byte[]> currentContracts = ContractsUtils.getFilesGroupByFilename(outputDir);
      Map<String, byte[]> sourceContracts = ContractsUtils.getFilesGroupByFilename(sourceContractsDir);

      currentContracts.forEach((contractName, swagger) -> {

        byte[] sourceSwagger = sourceContracts.get(contractName);

        ContractComparator contractComparator = new ContractComparator(new String(sourceSwagger), new String(swagger));

        if (!contractComparator.equals()) {
          getLog().info("contract is not matched, difference is as follows");
          getLog().info(sourceContractsDir + "/" + contractName + " vs " + outputDir + "/" + contractName);
          contractComparator.splitPrintToScreen();
        } else {
          getLog().info("succee, contract verification passed");
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
