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

import org.openapitools.codegen.CliOption;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.languages.SpringCodegen;

import io.swagger.codegen.mustache.CamelCaseLambda;

public class ServiceCombCodegen extends AbstractJavaCodegenExt {

  public static final String DEFAULT_LIBRARY = "SpringMVC";

  public static final String POJO_LIBRARY = "POJO";

  public static final String JAX_RS_LIBRARY = "JAX-RS";

  public static final String SPRING_BOOT_LIBRARY = "SpringBoot";

  private String applicationId = "defaultApp";

  private String serviceId = "defaultService";

  private String modelTemplateFolder = "model";

  @Override
  public CodegenType getTag() {
    return CodegenType.SERVER;
  }

  @Override
  public String getName() {
    return "ServiceComb";
  }

  @Override
  public String getHelp() {
    return "Generates a ServiceComb server library.";
  }

  public ServiceCombCodegen() {
    super();

    outputFolder = "generated-code/ServiceComb";

    embeddedTemplateDir = templateDir = "ServiceComb";
    modelTemplateFiles.put(modelTemplateFolder + "/model.mustache", ".java");
    modelTemplateFiles.remove("model.mustache");

    supportedLibraries.put(DEFAULT_LIBRARY, "ServiceComb Server application using the SpringMVC programming model.");
    supportedLibraries.put(POJO_LIBRARY, "ServiceComb Server application using the POJO programming model.");
    supportedLibraries.put(JAX_RS_LIBRARY, "ServiceComb Server application using the JAX-RS programming model.");
    supportedLibraries
        .put(SPRING_BOOT_LIBRARY, "ServiceComb Server application using the SpringBoot programming model.");

    setLibrary(DEFAULT_LIBRARY);

    CliOption library = new CliOption(CodegenConstants.LIBRARY, "library template (sub-template) to use");
    library.setEnum(supportedLibraries);
    library.setDefault(DEFAULT_LIBRARY);
    cliOptions.add(library);

    addDirectoryStrategy(new ProviderDirectoryStrategy(), ServiceType.PROVIDER.getValue());
    addDirectoryStrategy(new ConsumerDirectoryStrategy(), ServiceType.CONSUMER.getValue());
    addDirectoryStrategy(new DefaultDirectoryStrategy(), ServiceType.ALL.getValue());
  }

  @Override
  public String modelFileFolder() {
    return outputFolder + "/" + currentDirectoryStrategy.modelDirectory() + "/" + sourceFolder + "/" + modelPackage()
        .replace('.', '/');
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
  public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {

    Map operations = (Map) objs.get("operations");
    String classnameImpl = operations.get("classname") + "Impl";
    operations.put("classnameImpl", classnameImpl);
    additionalProperties.put("classnameImpl", classnameImpl);
    return super.postProcessOperationsWithModels(objs, allModels);
  }

  @Override
  public void processOpts() {

    super.processOpts();

    importMapping.put("OffsetDateTime", "java.time.OffsetDateTime");
    additionalProperties.put("dateLibrary", "java8");
    additionalProperties.put("camelcase", new CamelCaseLambda());
    additionalProperties.put("getGenericClassType", new GetGenericClassTypeLambda());
    additionalProperties.put("getRelativeBasePath", new GetRelativeBasePathLambda());
    additionalProperties.put("applicationId", applicationId);
    additionalProperties.put("library", getLibrary());
    additionalProperties.put("outputFolder", outputFolder);
    additionalProperties.put("apiTemplateFiles", apiTemplateFiles);
    additionalProperties.put("apiTestTemplateFiles", apiTestTemplateFiles);
    additionalProperties.put("modelTemplateFiles", modelTemplateFiles);
    additionalProperties.put("apiDocTemplateFiles", apiDocTemplateFiles);

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
  public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
    super.postProcessModelProperty(model, property);
    model.imports.remove("ApiModelProperty");
    model.imports.remove("ApiModel");
  }

  @Override
  public Map<String, Object> postProcessModelsEnum(Map<String, Object> objs) {
    objs = super.postProcessModelsEnum(objs);
    SpringCodegen springCodegen = new SpringCodegen();
    return springCodegen.postProcessModelsEnum(objs);
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
}