package org.apache.servicecomb.toolkit.codegen;

import java.util.List;

import org.openapitools.codegen.SupportingFile;

public abstract class AbstractProviderDirectoryStrategy extends AbstractDirectoryStrategy {

  @Override
  public void processSupportingFile(List<SupportingFile> supportingFiles) {
    correctServiceId();
  }

  private void correctServiceId() {
    propertiesMap
        .computeIfAbsent(GeneratorExternalConfigConstant.PROVIDER_SERVICE_ID, key -> propertiesMap.get("artifactId"));
  }
}
