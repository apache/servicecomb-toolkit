package org.apache.servicecomb.toolkit.codegen;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import io.swagger.codegen.CodegenType;
import io.swagger.codegen.SupportingFile;
import io.swagger.codegen.mustache.CamelCaseLambda;

public class SpringCloudCodegen extends AbstractJavaCodegenExt {

  private String providerProject = "provider";

  private String consumerProject = "consumer";

  private String modelProject = "model";

  private String registryProject = "eureka-server";

  private String applicationId = "defaultApp";

  private String microserviceName = "defaultService";

  private String consumerTemplateFolder = "consumer/openfeign";

  private String providerTemplateFolder = "provider/servlet";

  private String registryTemplateFolder = "service-registry/eureka-server";

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
  }

  @Override
  public void processOpts() {

    super.processOpts();

    importMapping.put("OffsetDateTime", "java.time.OffsetDateTime");
    additionalProperties.put("dateLibrary", "java8");
    if (StringUtils.isEmpty((String) additionalProperties.get("mainClassPackage"))) {
      additionalProperties.put("mainClassPackage", mainClassPackage);
    } else {
      mainClassPackage = (String) additionalProperties.get("mainClassPackage");
    }
    additionalProperties.put("camelcase", new CamelCaseLambda());
    additionalProperties.put("getGenericClassType", new GetGenericClassTypeLambda());
    additionalProperties.put("getRelativeBasePath", new GetRelativeBasePathLambda());
    additionalProperties.put("removeImplSuffix", new RemoveImplSuffixLambda());
    additionalProperties.put("applicationId", applicationId);

    if (additionalProperties.get("microserviceName") != null) {
      microserviceName = (String) additionalProperties.get("microserviceName");
    }
    additionalProperties.put("microserviceName", microserviceName);

    boolean isMultipleModule = (boolean) Optional
        .ofNullable(additionalProperties.get("isMultipleModule")).orElse(true);
    if (isMultipleModule) {
      processParentProjectOpts();
    }
    switch ((String) Optional.ofNullable(additionalProperties.get(ProjectMetaConstant.SERVICE_TYPE))
        .orElse("")) {
      case "provider":
        processProviderProjectOpts();
        break;
      case "consumer":
        processConsumerProjectOpts();
        break;
      case "all":
      default:
        processProviderProjectOpts();
        processConsumerProjectOpts();
    }
    processModelProjectOpts();
    processRegistryProjectOpts();
  }

  private void processModelProjectOpts() {

    additionalProperties
        .computeIfAbsent(GeneratorExternalConfigConstant.MODEL_ARTIFACT_ID, k -> modelProject);
    additionalProperties.put(GeneratorExternalConfigConstant.MODEL_PROJECT_NAME, modelProject);

    supportingFiles.add(new SupportingFile("model/pom.mustache",
        modelProject,
        "pom.xml")
    );
  }

  private void processParentProjectOpts() {

    supportingFiles.add(new SupportingFile("pom.mustache",
        "",
        "pom.xml")
    );
  }

  private void processProviderProjectOpts() {

    if (additionalProperties.get(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME) != null) {
      providerProject = (String) additionalProperties
          .get(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME);
    }

    additionalProperties.computeIfAbsent(GeneratorExternalConfigConstant.PROVIDER_ARTIFACT_ID,
        k -> providerProject);
    additionalProperties
        .put(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME, providerProject);

    additionalProperties.computeIfAbsent(GeneratorExternalConfigConstant.PROVIDER_SERVICE_ID, key -> providerProject);

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/applicationYml.mustache",
        resourcesFolder(providerProject),
        "application.yml"));

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/pom.mustache",
        providerProject,
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile(providerTemplateFolder + "/Application.mustache",
        mainClassFolder(providerProject),
        "Application.java")
    );
  }

  private void processConsumerProjectOpts() {

    if (additionalProperties.get(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME) != null) {
      consumerProject = (String) additionalProperties
          .get(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME);
    }

    additionalProperties.computeIfAbsent(GeneratorExternalConfigConstant.CONSUMER_ARTIFACT_ID,
        k -> consumerProject);

    additionalProperties
        .put(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME, consumerProject);

    additionalProperties.computeIfAbsent(GeneratorExternalConfigConstant.CONSUMER_SERVICE_ID, key -> consumerProject);

    apiTemplateFiles.put(apiConsumerTemplate, ".java");

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/applicationYml.mustache",
        resourcesFolder(consumerProject),
        "application.yml"));

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/pom.mustache",
        consumerProject,
        "pom.xml")
    );

    supportingFiles.add(new SupportingFile(consumerTemplateFolder + "/Application.mustache",
        mainClassFolder(consumerProject),
        "Application.java")
    );
  }

  private void processRegistryProjectOpts() {

    supportingFiles.add(new SupportingFile(registryTemplateFolder + "/applicationYml.mustache",
        resourcesFolder(registryProject),
        "application.yml"));

    supportingFiles
        .add(new SupportingFile(registryTemplateFolder + "/EurekaServerApplication.mustache",
            mainClassFolder(registryProject),
            "EurekaServerApplication.java"));

    supportingFiles.add(new SupportingFile(registryTemplateFolder + "/pom.mustache",
        registryProject,
        "pom.xml"));
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

  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + providerProject + "/" + sourceFolder + "/" + apiPackage()
        .replace('.', '/');
  }

  @Override
  public String apiFilename(String templateName, String tag) {
    if (apiConsumerTemplate.equals(templateName)) {
      String suffix = apiTemplateFiles().get(templateName);
      return apiConsumerFolder() + File.separator + toApiFilename(tag) + suffix;
    }

    return super.apiFilename(templateName, tag);
  }

  private String apiConsumerFolder() {
    return outputFolder + "/" + consumerProject + "/" + sourceFolder + "/" + apiPackage()
        .replace('.', '/');
  }

  @Override
  public String modelFileFolder() {
    return outputFolder + "/" + modelProject + "/" + sourceFolder + "/" + modelPackage()
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