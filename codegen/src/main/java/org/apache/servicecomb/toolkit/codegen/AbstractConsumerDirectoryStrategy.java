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
