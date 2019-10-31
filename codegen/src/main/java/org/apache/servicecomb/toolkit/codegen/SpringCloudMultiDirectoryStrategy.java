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
import java.util.Map;
import java.util.Optional;

import org.openapitools.codegen.SupportingFile;

public class SpringCloudMultiDirectoryStrategy extends AbstractMultiDirectoryStrategy {

  private String consumerTemplateFolder = "consumer/openfeign";

  private String providerTemplateFolder = "provider/servlet";

  private String apiConsumerTemplate = consumerTemplateFolder + "/apiConsumer.mustache";

  @Override
  public String modelDirectory() {
    return Optional.ofNullable((String) propertiesMap.get(GeneratorExternalConfigConstant.MODEL_PROJECT_NAME))
        .orElse("model");
  }

  @Override
  public String providerDirectory() {
    return Optional.ofNullable((String) propertiesMap.get(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME))
        .orElse("provider");
  }

  @Override
  public String consumerDirectory() {
    return Optional.ofNullable((String) propertiesMap.get(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME))
        .orElse("consumer");
  }

  @Override
  public void processSupportingFile(List<SupportingFile> supportingFiles) {
    super.processSupportingFile(supportingFiles);
    processProvider(supportingFiles);
    processConsumer(supportingFiles);
    processModel(supportingFiles);
    propertiesMap.put("isMultipleModule", true);

    supportingFiles.add(new SupportingFile("project/pom.mustache",
        "",
        "pom.xml")
    );
  }


  private void processModel(List<SupportingFile> supportingFiles) {

    propertiesMap
        .computeIfAbsent(GeneratorExternalConfigConstant.MODEL_ARTIFACT_ID, k -> modelDirectory());
    propertiesMap.put(GeneratorExternalConfigConstant.MODEL_PROJECT_NAME, modelDirectory());

    supportingFiles.add(new SupportingFile("model/pom.mustache",
        modelDirectory(),
        "pom.xml")
    );
  }

  private void processProvider(List<SupportingFile> supportingFiles) {

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/applicationYml.mustache",
        resourcesFolder(providerDirectory()),
        "application.yml"));

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/pom.mustache",
        providerDirectory(),
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/Application.mustache",
        mainClassFolder(providerDirectory()),
        "Application.java")
    );

    propertiesMap.computeIfAbsent(GeneratorExternalConfigConstant.PROVIDER_ARTIFACT_ID,
        k -> providerDirectory());
    propertiesMap
        .put(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME, providerDirectory());
  }

  private void processConsumer(List<SupportingFile> supportingFiles) {

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/applicationYml.mustache",
        resourcesFolder(consumerDirectory()),
        "application.yml"));

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/pom.mustache",
        consumerDirectory(),
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/Application.mustache",
        mainClassFolder(consumerDirectory()),
        "Application.java")
    );

    propertiesMap.computeIfAbsent(GeneratorExternalConfigConstant.CONSUMER_ARTIFACT_ID,
        k -> consumerDirectory());

    propertiesMap
        .put(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME, consumerDirectory());

    propertiesMap.put(apiConsumerTemplate, ServiceType.CONSUMER.getValue());

    Map<String, String> apiTemplateFiles = ((Map<String, String>) propertiesMap.get("apiTemplateFiles"));
    apiTemplateFiles.put(apiConsumerTemplate, ".java");
  }
}
