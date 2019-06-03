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
import java.util.List;
import java.util.Map;

import io.swagger.codegen.CliOption;
import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenConstants;
import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.CodegenProperty;
import io.swagger.codegen.CodegenType;
import io.swagger.codegen.SupportingFile;
import io.swagger.codegen.languages.AbstractJavaCodegen;
import io.swagger.codegen.languages.SpringCodegen;
import io.swagger.codegen.mustache.CamelCaseLambda;

public class ServiceCombCodegen extends AbstractJavaCodegen implements CodegenConfig {

  private static final String DEFAULT_LIBRARY = "SpringMVC";

  private static final String POJO_LIBRARY = "POJO";

  private static final String JAX_RS_LIBRARY = "JAX-RS";

  private static final String SPRING_BOOT_LIBRARY = "SpringBoot";

  private String mainClassPackage;

  private String providerProject = "provider";

  private String consumerProject = "consumer";

  private String modelProject = "model";

  private String applicationId = "defaultApp";

  private String microserviceName = "defaultService";

  private String consumerTemplateFolder = "consumer";

  private String providerTemplateFolder = "provider";

  private String modelTemplateFolder = "model";

  private String apiConsumerTemplate = consumerTemplateFolder + "/apiConsumer.mustache";

  private String apiConsumerTemplateForPojo = consumerTemplateFolder + "/pojo/apiConsumer.mustache";

  private String modelConsumerTemplate = consumerTemplateFolder + "/model.mustache";

  private String pojoApiImplTemplate = "apiImpl.mustache";

  private int modelSwitch = 1;


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

    modelDocTemplateFiles.remove("model_doc.mustache");
    apiDocTemplateFiles.remove("api_doc.mustache");
    apiTestTemplateFiles.remove("api_test.mustache");

    embeddedTemplateDir = templateDir = "ServiceComb";
    modelTemplateFiles.put(modelTemplateFolder + "/model.mustache", ".java");
    modelTemplateFiles.remove("model.mustache");

    groupId = "org.apache.servicecomb";
    artifactId = "app";

    apiPackage = groupId + "." + artifactId + ".api";
    modelPackage = groupId + "." + artifactId + ".model";
    mainClassPackage = groupId + "." + artifactId;

    supportedLibraries.put(DEFAULT_LIBRARY, "ServiceComb Server application using the springboot programming model.");
    supportedLibraries.put(POJO_LIBRARY, "ServiceComb Server application using the pojo programming model.");
    supportedLibraries.put(JAX_RS_LIBRARY, "ServiceComb Server application using the jax-rs programming model.");
    supportedLibraries
        .put(SPRING_BOOT_LIBRARY, "ServiceComb Server application using the SpringBoot programming model.");

    setLibrary(DEFAULT_LIBRARY);

    CliOption library = new CliOption(CodegenConstants.LIBRARY, "library template (sub-template) to use");
    library.setDefault(DEFAULT_LIBRARY);
    library.setEnum(supportedLibraries);
    library.setDefault(DEFAULT_LIBRARY);
    cliOptions.add(library);
  }

  @Override
  public String modelFileFolder() {
    return outputFolder + "/" + modelProject + "/" + sourceFolder + "/" + modelPackage().replace('.', '/');
  }

  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + providerProject + "/" + sourceFolder + "/" + apiPackage().replace('.', '/');
  }

  @Override
  public String apiFilename(String templateName, String tag) {
    if (apiConsumerTemplate.equals(templateName) || apiConsumerTemplateForPojo.equals(templateName)) {
      String suffix = apiTemplateFiles().get(templateName);
      return apiConsumerFolder() + File.separator + toApiFilename(tag) + suffix;
    }

    if (POJO_LIBRARY.equals(getLibrary())) {
      if ("apiImpl.mustache".equals(templateName)) {
        String suffix = apiTemplateFiles().get(templateName);
        return apiFileFolder() + File.separator + additionalProperties.get("classnameImpl") + suffix;
      }
      if ("api.mustache".equals(templateName)) {
        String suffix = apiTemplateFiles().get(templateName);
        return pojoApiInterfaceFolder() + File.separator + camelize(tag) + "Api" + suffix;
      }
    }
    return super.apiFilename(templateName, tag);
  }

  private String pojoApiInterfaceFolder() {
    return outputFolder + "/" + modelProject + "/" + sourceFolder + "/" + apiPackage().replace('.', '/');
  }

  private String apiConsumerFolder() {
    return outputFolder + "/" + consumerProject + "/" + sourceFolder + "/" + apiPackage().replace('.', '/');
  }

  @Override
  public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {

    Map operations = (Map) objs.get("operations");
    String classnameImpl = (String) operations.get("classname") + "Impl";
    operations.put("classnameImpl", classnameImpl);
    additionalProperties.put("classnameImpl", classnameImpl);
    return super.postProcessOperationsWithModels(objs, allModels);
  }

  @Override
  public void processOpts() {

    super.processOpts();

    importMapping.put("OffsetDateTime", "java.time.OffsetDateTime");
    additionalProperties.put("dateLibrary", "java8");
    additionalProperties.put("mainClassPackage", mainClassPackage);
    additionalProperties.put("camelcase", new CamelCaseLambda());
    additionalProperties.put("getGenericClassType", new GetGenericClassTypeLambda());
    additionalProperties.put("removeImplSuffix", new RemoveImplSuffixLambda());
    additionalProperties.put("applicationId", applicationId);
    additionalProperties.put("microserviceName", microserviceName);

    processParentProjectOpts();
    processProviderProjectOpts();
    processConsumerOpts();
    processModelProjectOpts();
    processPojo();
  }

  private void processPojo() {
    if (!POJO_LIBRARY.equals(getLibrary())) {
      return;
    }
    apiTemplateFiles.put(pojoApiImplTemplate, ".java");
    apiTemplateFiles.remove(apiConsumerTemplate);
    apiTemplateFiles.put(apiConsumerTemplateForPojo, "Consumer.java");
    additionalProperties.put("isPOJO", true);
  }

  @Override
  public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
    super.postProcessModelProperty(model, property);
    model.imports.remove("ApiModelProperty");
    model.imports.remove("ApiModel");
  }

  private void processModelProjectOpts() {
    supportingFiles.add(new SupportingFile("model/pom.mustache",
        modelProject,
        "pom.xml")
    );
  }

  private void processParentProjectOpts() {

    supportingFiles.add(new SupportingFile("project/pom.mustache",
        "",
        "pom.xml")
    );
  }

  private void processProviderProjectOpts() {
    supportingFiles.add(new SupportingFile("pom.mustache",
        providerProject,
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile("Application.mustache",
        mainClassFolder(providerProject),
        "Application.java")
    );

    supportingFiles.add(new SupportingFile("log4j2.mustache",
        resourcesFolder(providerProject),
        "log4j2.xml")
    );

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/microservice.mustache",
        resourcesFolder(providerProject),
        "microservice.yaml")
    );

    apiTemplateFiles.put(apiConsumerTemplate, "Consumer.java");
  }

  private void processConsumerOpts() {

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/pom.mustache",
        consumerProject,
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/Application.mustache",
        mainClassFolder(consumerProject),
        "Application.java")
    );

    supportingFiles.add(new SupportingFile("log4j2.mustache",
        resourcesFolder(consumerProject),
        "log4j2.xml")
    );

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/microservice.mustache",
        resourcesFolder(consumerProject),
        "microservice.yaml")
    );
  }

  @Override
  public Map<String, Object> postProcessModelsEnum(Map<String, Object> objs) {
    objs = super.postProcessModelsEnum(objs);
    SpringCodegen springCodegen = new SpringCodegen();
    return springCodegen.postProcessModelsEnum(objs);
  }

  @Override
  public Map<String, Object> postProcessOperations(Map<String, Object> objs) {
    SpringCodegen springCodegen = new SpringCodegen();
    return springCodegen.postProcessOperations(objs);
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

    return initialCaps(name) + "Api";
  }

  private String mainClassFolder(String projectPath) {
    return projectPath + File.separator + sourceFolder + File.separator + mainClassPackage.replace(".", File.separator);
  }

  private String resourcesFolder(String projectPath) {
    return projectPath + File.separator + projectFolder + File.separator + "resources";
  }
}