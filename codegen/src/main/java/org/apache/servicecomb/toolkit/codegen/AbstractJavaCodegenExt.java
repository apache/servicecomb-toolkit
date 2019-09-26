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

import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.languages.AbstractJavaCodegen;
import org.openapitools.codegen.languages.SpringCodegen;

public abstract class AbstractJavaCodegenExt extends AbstractJavaCodegen {

  protected String mainClassPackage;

  public AbstractJavaCodegenExt() {

    modelDocTemplateFiles.remove("model_doc.mustache");
    apiDocTemplateFiles.remove("api_doc.mustache");
    apiTestTemplateFiles.remove("api_test.mustache");

    groupId = "domain.orgnization.project";
    artifactId = "sample";

    apiPackage = groupId + "." + artifactId + ".api";
    modelPackage = groupId + "." + artifactId + ".model";
    mainClassPackage = groupId + "." + artifactId;
  }

  @Override
  public Map<String, Object> postProcessModelsEnum(Map<String, Object> objs) {
    objs = super.postProcessModelsEnum(objs);
    SpringCodegen springCodegen = new SpringCodegen();
    return springCodegen.postProcessModelsEnum(objs);
  }

  @Override
  public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {
    SpringCodegen springCodegen = new SpringCodegen();
    return springCodegen.postProcessOperationsWithModels(objs, allModels);
  }

  @Override
  public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
    super.postProcessModelProperty(model, property);
    model.imports.remove("ApiModelProperty");
    model.imports.remove("ApiModel");
  }

  protected String mainClassFolder(String projectPath) {
    return projectPath + File.separator + sourceFolder + File.separator + mainClassPackage
        .replace(".", File.separator);
  }

  protected String resourcesFolder(String projectPath) {
    return projectPath + File.separator + projectFolder + File.separator + "resources";
  }

  @Override
  public String templateDir() {
    return getTemplatingEngine().getIdentifier() + File.separator + super.templateDir();
  }

  @Override
  public String embeddedTemplateDir() {
    return templateDir();
  }
}
