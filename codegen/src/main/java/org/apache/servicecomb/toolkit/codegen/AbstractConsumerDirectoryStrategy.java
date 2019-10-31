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

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.SupportingFile;

public abstract class AbstractConsumerDirectoryStrategy extends AbstractDirectoryStrategy {

  @Override
  public void processSupportingFile(List<SupportingFile> supportingFiles) {
    correctServiceId();
  }

  private void correctServiceId() {
    String serviceId = (String) propertiesMap.get(ProjectMetaConstant.SERVICE_ID);
    propertiesMap.computeIfAbsent(GeneratorExternalConfigConstant.CONSUMER_SERVICE_ID, key -> {
      if (StringUtils.isNotEmpty(serviceId)) {
        return serviceId;
      }
      return propertiesMap.get("artifactId");
    });
    propertiesMap.computeIfAbsent(GeneratorExternalConfigConstant.PROVIDER_SERVICE_ID, key -> {
      throw new UnsupportedOperationException("In consumer type, providerServiceId is required");
    });
  }
}
