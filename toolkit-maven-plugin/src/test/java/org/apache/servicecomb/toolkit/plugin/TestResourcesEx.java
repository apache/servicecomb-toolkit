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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.servicecomb.toolkit.common.ClassMaker;

class TestResourcesEx {

  private String basedirWithContract;

  private String basedirWithoutContract;

  private String contractLocation;

  private String contractDestination;

  private String basedirMultiModule;

  private AbstractMojo testMojo;

  private MojoRule rule;

  TestResourcesEx(TestResources resources) throws Exception {

    this.basedirWithContract = resources.getBasedir("demo-with-contract") + File.separator;
    this.basedirWithoutContract = resources.getBasedir("demo-without-contract") + File.separator;
    this.contractLocation = resources.getBasedir("contract-source") + File.separator;
    this.contractDestination = resources.getBasedir("contract-destination") + File.separator;
    this.basedirMultiModule = resources.getBasedir("demo-multi-module") + File.separator;
  }

  void createMojo(MojoRule rule, String pluginGoal) {
    this.rule = rule;
    this.testMojo = mockMojo(pluginGoal);
  }

  void setVariableValueToObject(String variable, Object value) throws IllegalAccessException {
    this.rule.setVariableValueToObject(this.testMojo, variable, value);
  }

  String getVariableValueFromObject(@SuppressWarnings("SameParameterValue") String variable)
      throws IllegalAccessException {
    return (String) this.rule.getVariableValueFromObject(this.testMojo, variable);
  }

  String getBasedirWithContract() {
    return basedirWithContract;
  }

  String getBasedirWithoutContract() {
    return basedirWithoutContract;
  }

  String getBasedirMultiModule() {
    return basedirMultiModule;
  }

  String getContractLocation() {
    return contractLocation;
  }

  String getContractDestination() {
    return contractDestination;
  }

  void execute() throws MojoFailureException, MojoExecutionException {
    this.testMojo.execute();
  }

  List<String> getRuntimeUrlPath(String basedir) throws InterruptedException, TimeoutException, IOException {

    ClassMaker.compile(basedir);

    List<String> runtimeUrlPath = new ArrayList<>();

    runtimeUrlPath.add(basedir + "target" + File.separator + "classes");

    return runtimeUrlPath;
  }

  private AbstractMojo mockMojo(String pluginGoal) {
    switch (pluginGoal) {
      case "generate":
        return new GenerateMojo();
      case "verify":
        return new VerifyMojo();
      default:
        throw new RuntimeException("undefined plugin goal");
    }
  }
}
