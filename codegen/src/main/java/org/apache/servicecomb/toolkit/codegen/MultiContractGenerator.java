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

package org.apache.servicecomb.toolkit.codegen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.Generator;

public class MultiContractGenerator extends DefaultGenerator {

  private List<ClientOptInput> optsList = new ArrayList<>();

  public Generator addOpts(ClientOptInput opts) {
    optsList.add(opts);
    return this;
  }

  public void generateParentProject(List<File> files, List<Map<String, Object>> modules) {

    this.config = optsList.get(0).getConfig();
    String outputFilename = opts.getConfig().outputFolder() + File.separator + "pom.xml";

    if (!config.shouldOverwrite(outputFilename)) {
      LOGGER.info("Skipped overwriting " + outputFilename);
    }
    Map<String, Object> templateData = this.config.additionalProperties();
    templateData.put("modules", modules);
    try {
      files.add(processTemplateToFile(templateData, "project/pom.mustache", outputFilename));
    } catch (IOException e) {
      throw new RuntimeException("Failed to generate parent project pom.xml", e);
    }
  }

  @Override
  public List<File> generate() {

    if (optsList == null || optsList.size() == 0) {
      return null;
    }

    List<File> fileList = new ArrayList<>();
    List<Map<String, Object>> modules = new ArrayList<>();

    Set<Object> moduleSet = new HashSet<>();
    for (ClientOptInput opts : optsList) {
      moduleSet.add(opts.getConfig().additionalProperties().get(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME));
      moduleSet.add(opts.getConfig().additionalProperties().get(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME));
      moduleSet.add(opts.getConfig().additionalProperties().get(GeneratorExternalConfigConstant.MODEL_PROJECT_NAME));
      this.opts(opts);
      fileList.addAll(super.generate());
    }

    moduleSet.forEach(module -> {
      modules.add(Collections.singletonMap("module", module));
    });

    if (ServiceType.ALL.getValue().equals(
        Optional.ofNullable(opts.getConfig().additionalProperties().get(ProjectMetaConstant.SERVICE_TYPE))
            .orElse(ServiceType.ALL.getValue()))) {
      generateParentProject(fileList, modules);
    }

    return fileList;
  }
}
