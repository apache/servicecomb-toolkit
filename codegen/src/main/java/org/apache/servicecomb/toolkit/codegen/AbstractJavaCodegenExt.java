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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.languages.AbstractJavaCodegen;
import org.openapitools.codegen.languages.SpringCodegen;

public abstract class AbstractJavaCodegenExt extends AbstractJavaCodegen {

  protected final Map<String, DirectoryStrategy> directoryStrategyMap = new LinkedHashMap<>();

  protected DirectoryStrategy currentDirectoryStrategy;

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
  public void processOpts() {
    super.processOpts();
    if (StringUtils.isEmpty((String) additionalProperties.get("mainClassPackage"))) {
      mainClassPackage = apiPackage.substring(0, apiPackage.lastIndexOf("."));
      additionalProperties.put("mainClassPackage", mainClassPackage);
    } else {
      mainClassPackage = (String) additionalProperties.get("mainClassPackage");
    }
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


  /**
   * Register a custom VersionStrategy to apply to resource URLs that match the
   * given path patterns.
   * @param strategy the custom strategy
   * @param serviceTypes one or more service type,
   * relative to the service type that represent microservice type of generated microservice project
   * @see DirectoryStrategy
   */
  public void addDirectoryStrategy(DirectoryStrategy strategy, String... serviceTypes) {
    for (String serviceType : serviceTypes) {
      getStrategyMap().put(serviceType, strategy);
    }
  }

  /**
   * Return the map with directory strategies keyed by service type.
   */
  public Map<String, DirectoryStrategy> getStrategyMap() {
    return this.directoryStrategyMap;
  }
}
