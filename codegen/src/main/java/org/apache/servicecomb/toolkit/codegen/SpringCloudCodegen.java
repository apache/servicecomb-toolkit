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

import static org.openapitools.codegen.utils.StringUtils.camelize;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.templating.mustache.CamelCaseLambda;

public class SpringCloudCodegen extends AbstractJavaCodegenExt {

  private String applicationId = "defaultApp";

  private String serviceId = "defaultService";

  private String consumerTemplateFolder = "consumer/openfeign";

  private String providerTemplateFolder = "provider/servlet";

  private String modelTemplateFolder = "model";

  private String apiConsumerTemplate = consumerTemplateFolder + "/apiConsumer.mustache";

  public SpringCloudCodegen() {

    super();
    outputFolder = "generated-code/SpringCloud";
    apiTemplateFiles.remove("api.mustache");
    apiTemplateFiles.put(providerTemplateFolder + "/api.mustache", ".java");
    embeddedTemplateDir = templateDir = "SpringCloud";
    modelTemplateFiles.put(modelTemplateFolder + "/model.mustache", ".java");
    modelTemplateFiles.remove("model.mustache");

    addDirectoryStrategy(new SpringCloudProviderDirectoryStrategy(), ServiceType.PROVIDER.getValue());
    addDirectoryStrategy(new SpringCloudConsumerDirectoryStrategy(), ServiceType.CONSUMER.getValue());
    addDirectoryStrategy(new SpringCloudMultiDirectoryStrategy(), ServiceType.ALL.getValue());
  }

  @Override
  public void processOpts() {

    super.processOpts();

    importMapping.put("OffsetDateTime", "java.time.OffsetDateTime");
    additionalProperties.put("dateLibrary", "java8");
    additionalProperties.put("camelcase", new CamelCaseLambda());
    additionalProperties.put("apiTemplateFiles", apiTemplateFiles);
    additionalProperties.put("getGenericClassType", new GetGenericClassTypeLambda());
    additionalProperties.put("getRelativeBasePath", new GetRelativeBasePathLambda());
    additionalProperties.put("showBasePath", new ShowBasePathLambda());
    additionalProperties.put("applicationId", applicationId);

    if (additionalProperties.get(ProjectMetaConstant.SERVICE_ID) != null) {
      serviceId = (String) additionalProperties.get(ProjectMetaConstant.SERVICE_ID);
    }
    additionalProperties.put(ProjectMetaConstant.SERVICE_ID, serviceId);

    currentDirectoryStrategy = getStrategyMap()
        .get(Optional.ofNullable(additionalProperties.get(ProjectMetaConstant.SERVICE_TYPE))
            .orElse(ServiceType.ALL.getValue()));

    // when all additionalProperties are processed
    currentDirectoryStrategy.addCustomProperties(additionalProperties);
    currentDirectoryStrategy.processSupportingFile(supportingFiles);
  }

  @Override
  public String toApiName(String name) {
    if (name.length() == 0) {
      return "DefaultApi";
    }

    String apiName = (String) additionalProperties.get("apiName");
    if (apiName != null) {
      return apiName;
    }

    return camelize(name) + "Api";
  }

  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + currentDirectoryStrategy.providerDirectory() + "/" + sourceFolder + "/" + apiPackage()
        .replace('.', '/');
  }

  @Override
  public String apiFilename(String templateName, String tag) {
    if (ServiceType.CONSUMER.getValue().equals(additionalProperties.get(ProjectMetaConstant.SERVICE_TYPE))
        || ServiceType.CONSUMER.getValue().equals(additionalProperties.get(templateName))) {
      String suffix = apiTemplateFiles().get(templateName);
      return apiConsumerFolder() + File.separator + toApiFilename(tag) + suffix;
    }

    return super.apiFilename(templateName, tag);
  }

  private String apiConsumerFolder() {
    return outputFolder + "/" + currentDirectoryStrategy.consumerDirectory() + "/" + sourceFolder + "/" + apiPackage()
        .replace('.', '/');
  }

  @Override
  public String modelFileFolder() {
    return outputFolder + "/" + currentDirectoryStrategy.modelDirectory() + "/" + sourceFolder + "/" + modelPackage()
        .replace('.', '/');
  }

  @Override
  public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs,
      List<Object> allModels) {

    Map operations = (Map) objs.get("operations");
    String classnameImpl = (String) operations.get("classname") + "Impl";
    operations.put("classnameImpl", classnameImpl);
    additionalProperties.put("classnameImpl", classnameImpl);
    return super.postProcessOperationsWithModels(objs, allModels);
  }

  @Override
  public CodegenType getTag() {
    return CodegenType.SERVER;
  }

  @Override
  public String getName() {
    return "SpringCloud";
  }

  @Override
  public String getHelp() {
    return "Generates a SpringCloud server library.";
  }
}