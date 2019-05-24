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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.common.ContractsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractGenerator {

  private static Logger LOGGER = LoggerFactory.getLogger(ContractGenerator.class);

  private MavenProject project;

  public ContractGenerator(MavenProject project) {
    this.project = project;
  }

  public void generateAndOutput(String outputDir, String format) {

    List runtimeClasspaths = null;
    try {
      runtimeClasspaths = project.getRuntimeClasspathElements();
    } catch (DependencyResolutionRequiredException e) {
      LOGGER.error(e.getMessage());
    }
    URL[] runtimeUrls = new URL[runtimeClasspaths.size()];
    for (int i = 0; i < runtimeClasspaths.size(); i++) {
      String element = (String) runtimeClasspaths.get(i);
      try {
        runtimeUrls[i] = new File(element).toURI().toURL();
      } catch (MalformedURLException e) {
        LOGGER.error(e.getMessage());
      }
    }

    ContractsUtils.generateAndOutputContracts(outputDir, format, runtimeUrls);
  }
}
