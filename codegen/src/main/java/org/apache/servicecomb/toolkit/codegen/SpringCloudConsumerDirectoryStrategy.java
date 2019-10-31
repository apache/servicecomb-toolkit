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

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.SupportingFile;

public class SpringCloudConsumerDirectoryStrategy extends AbstractConsumerDirectoryStrategy {

  private String consumerTemplateFolder = "consumer/openfeign";

  private String apiConsumerTemplate = consumerTemplateFolder + "/apiConsumer.mustache";

  @Override
  public String modelDirectory() {
    return consumerDirectory();
  }

  @Override
  public String providerDirectory() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String consumerDirectory() {
    return (String) propertiesMap.get("artifactId");
  }

  @Override
  public void processSupportingFile(List<SupportingFile> supportingFiles) {

    super.processSupportingFile(supportingFiles);

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
    apiTemplateFiles.remove("api.mustache");
    apiTemplateFiles.put(apiConsumerTemplate, ".java");
  }
}
