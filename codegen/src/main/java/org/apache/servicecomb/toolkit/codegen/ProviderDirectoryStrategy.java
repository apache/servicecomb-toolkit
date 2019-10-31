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

import java.util.List;

import org.openapitools.codegen.SupportingFile;

public class ProviderDirectoryStrategy extends AbstractProviderDirectoryStrategy {

  protected String providerTemplateFolder = "provider";

  @Override
  public String modelDirectory() {
    return providerDirectory();
  }

  @Override
  public String providerDirectory() {
    return (String) propertiesMap.get("artifactId");
  }

  @Override
  public String consumerDirectory() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void processSupportingFile(List<SupportingFile> supportingFiles) {
    super.processSupportingFile(supportingFiles);
    supportingFiles.add(new SupportingFile("pom.mustache",
        providerDirectory(),
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile("Application.mustache",
        mainClassFolder(providerDirectory()),
        "Application.java")
    );

    supportingFiles.add(new SupportingFile("log4j2.mustache",
        resourcesFolder(providerDirectory()),
        "log4j2.xml")
    );

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/microservice.mustache",
        resourcesFolder(providerDirectory()),
        "microservice.yaml")
    );

    propertiesMap
        .computeIfAbsent(GeneratorExternalConfigConstant.PROVIDER_ARTIFACT_ID, k -> propertiesMap.get("artifactId"));
    propertiesMap
        .put(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME, providerDirectory());

    if (ServiceCombCodegen.POJO_LIBRARY.equals(propertiesMap.get("library"))) {
//      ((Map<String, String>) propertiesMap.get("apiTemplateFiles")).put(pojoApiImplTemplate, ".java");
      propertiesMap.put("isPOJO", true);
    }
    propertiesMap.put("isMultipleModule", false);
  }
}
