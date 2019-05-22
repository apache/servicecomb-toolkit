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
import java.util.Map;

import io.swagger.codegen.CliOption;
import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenConstants;
import io.swagger.codegen.CodegenType;
import io.swagger.codegen.SupportingFile;
import io.swagger.codegen.languages.AbstractJavaCodegen;
import io.swagger.codegen.languages.SpringCodegen;
import io.swagger.codegen.mustache.CamelCaseLambda;

public class ServiceCombProviderCodegen extends AbstractJavaCodegen implements CodegenConfig {

  private static final String DEFAULT_LIBRARY = "SpringMVC";

  private String resourcesFolder = projectFolder + File.separator + "resources";

  private String mainClassPackage;


  @Override
  public CodegenType getTag() {
    return CodegenType.SERVER;
  }

  @Override
  public String getName() {
    return "ServiceCombProvider";
  }

  @Override
  public String getHelp() {
    return "Generates a ServiceComb server library.";
  }

  public ServiceCombProviderCodegen() {
    super();

    outputFolder = "generated-code/ServiceCombProvider";

    modelDocTemplateFiles.remove("model_doc.mustache");
    apiDocTemplateFiles.remove("api_doc.mustache");
    apiTestTemplateFiles.remove("api_test.mustache");

    embeddedTemplateDir = templateDir = "ServiceCombProvider";

    groupId = "org.apache.servicecomb";
    artifactId = "example";

    apiPackage = groupId + ".example.controller";
    modelPackage = groupId + ".example.model";
    mainClassPackage = groupId + ".example";

    supportedLibraries.put(DEFAULT_LIBRARY, "ServiceComb Server application using the springboot programming model.");

    setLibrary(DEFAULT_LIBRARY);

    CliOption library = new CliOption(CodegenConstants.LIBRARY, "library template (sub-template) to use");
    library.setDefault(DEFAULT_LIBRARY);
    library.setEnum(supportedLibraries);
    library.setDefault(DEFAULT_LIBRARY);
    cliOptions.add(library);
  }

  @Override
  public void processOpts() {

    super.processOpts();

    importMapping.put("OffsetDateTime", "java.time.OffsetDateTime");
    additionalProperties.put("dateLibrary", "java8");
    additionalProperties.put("jackson", "true");
    additionalProperties.put("mainClassPackage", mainClassPackage);
    additionalProperties.put("camelcase", new CamelCaseLambda());

    supportingFiles.add(new SupportingFile("pom.mustache",
        "",
        "pom.xml")
    );
    supportingFiles.add(new SupportingFile("README.mustache",
        "",
        "README.md")
    );


    supportingFiles.add(new SupportingFile("Application.mustache",
        mainClassFolder(),
        "Application.java")
    );

    supportingFiles.add(new SupportingFile("log4j2.mustache",
        resourcesFolder,
        "log4j2.xml")
    );
    supportingFiles.add(new SupportingFile("microservice.mustache",
        resourcesFolder,
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
      return "DefaultController";
    }

    String apiName = (String) additionalProperties.get("apiName");
    if (apiName != null) {
      return apiName;
    }

    return initialCaps(name) + "Controller";
  }

  private String mainClassFolder() {
    return sourceFolder + File.separator + mainClassPackage.replace(".", File.separator);
  }
}