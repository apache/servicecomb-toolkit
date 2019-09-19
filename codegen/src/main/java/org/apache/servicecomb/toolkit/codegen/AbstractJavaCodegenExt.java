package org.apache.servicecomb.toolkit.codegen;

import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.CodegenProperty;
import io.swagger.codegen.languages.AbstractJavaCodegen;
import io.swagger.codegen.languages.SpringCodegen;
import java.io.File;
import java.util.Map;

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
  public Map<String, Object> postProcessOperations(Map<String, Object> objs) {
    SpringCodegen springCodegen = new SpringCodegen();
    return springCodegen.postProcessOperations(objs);
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

}
