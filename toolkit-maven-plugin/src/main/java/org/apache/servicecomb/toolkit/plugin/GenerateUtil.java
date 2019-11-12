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

package org.apache.servicecomb.toolkit.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.CodeGenerator;
import org.apache.servicecomb.toolkit.ContractsGenerator;
import org.apache.servicecomb.toolkit.DocGenerator;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.apache.servicecomb.toolkit.codegen.GeneratorExternalConfigConstant;
import org.apache.servicecomb.toolkit.codegen.MicroServiceFramework;
import org.apache.servicecomb.toolkit.codegen.ProjectMetaConstant;
import org.openapitools.codegen.config.CodegenConfigurator;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

class GenerateUtil {

  private static String providerProjectNameSuffix = "-provider";

  private static String consumerProjectNameSuffix = "-consumer";

  private static String modelProjectNameSuffix = "-model";

  public static void generateContract(MavenProject project, String contractOutput, String contractFileType,
      String type) {

    Map<String, Object> contractConfig = new HashMap<>();
    try {
      contractConfig.put("classpathUrls", project.getRuntimeClasspathElements());
    } catch (DependencyResolutionRequiredException e) {
      throw new RuntimeException("Failed to get runtime class elements", e);
    }
    contractConfig.put("outputDir", contractOutput);
    contractConfig.put("contractFileType", contractFileType);

    // TODO: support users to addParamCtx other getGenerator type soon
    ContractsGenerator contractGenerator = GeneratorFactory.getGenerator(ContractsGenerator.class, type);
    Objects.requireNonNull(contractGenerator).configure(contractConfig);

    contractGenerator.generate();
  }

  static void generateDocument(String contractLocation, String documentOutput, String type) throws IOException {

    // TODO: support users to addParamCtx other getGenerator type soon
    DocGenerator docGenerator = GeneratorFactory.getGenerator(DocGenerator.class, type);
    if (docGenerator == null) {
      throw new RuntimeException("Cannot found document generator's implementation");
    }

    Files.walkFileTree(Paths.get(contractLocation), new SimpleFileVisitor<Path>() {

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if (Files.isDirectory(file)) {
          return super.visitFile(file, attrs);
        }
        Map<String, Object> docGeneratorConfig = new HashMap<>();

        SwaggerParseResult swaggerParseResult = new OpenAPIParser()
            .readLocation(file.toUri().toURL().toString(), null, null);
        OpenAPI openAPI = swaggerParseResult.getOpenAPI();
        if (openAPI == null) {
          return super.visitFile(file, attrs);
        }
        docGeneratorConfig.put("contractContent", openAPI);
        docGeneratorConfig.put("outputPath",
            documentOutput + File.separator + file.getParent().toFile().getName() + File.separator + file.toFile()
                .getName()
                .substring(0, file.toFile().getName().indexOf(".")));

        docGenerator.configure(docGeneratorConfig);
        docGenerator.generate();

        return super.visitFile(file, attrs);
      }
    });
  }

  static void generateCode(ServiceConfig service, String contractLocation,
      String codeOutput, Map<String, Object> externalConfig, String type) throws IOException {
    CodeGenerator codeGenerator = GeneratorFactory.getGenerator(CodeGenerator.class, type);
    if (codeGenerator == null) {
      throw new RuntimeException("Cannot found code generator's implementation");
    }

    File contractFile = new File(contractLocation);
    if (contractFile.isDirectory()) {

      List<CodegenConfigurator> configurators = new ArrayList<>();
      Files.walkFileTree(Paths.get(contractFile.toURI()), new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          if (Files.isDirectory(file)) {
            return super.visitFile(file, attrs);
          }

          CodegenConfigurator configurator = new CodegenConfigurator();
          commonConfig(configurator, service);
          configurator.setOutputDir(codeOutput)
              .addAdditionalProperty(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME,
                  file.getParent().getFileName() + providerProjectNameSuffix)
              .addAdditionalProperty(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME,
                  file.getParent().getFileName() + consumerProjectNameSuffix)
              .addAdditionalProperty(GeneratorExternalConfigConstant.MODEL_PROJECT_NAME,
                  file.getParent().getFileName() + modelProjectNameSuffix)
              .addAdditionalProperty("apiName", file.toFile().getName().split("\\.")[0])
              .addAdditionalProperty("microserviceName", file.getParent().getFileName().toString());

          configurator.setInputSpec(file.toFile().getCanonicalPath());
          configurators.add(configurator);
          return super.visitFile(file, attrs);
        }
      });

      Objects.requireNonNull(codeGenerator).configure(Collections.singletonMap("configurators", configurators));
      codeGenerator.generate();
    } else {

      CodegenConfigurator configurator = new CodegenConfigurator();
      commonConfig(configurator, service);
      configurator.setOutputDir(codeOutput)
          .addAdditionalProperty(GeneratorExternalConfigConstant.PROVIDER_PROJECT_NAME,
              contractFile.getParentFile().getName() + providerProjectNameSuffix)
          .addAdditionalProperty(GeneratorExternalConfigConstant.CONSUMER_PROJECT_NAME,
              contractFile.getParentFile().getName() + consumerProjectNameSuffix)
          .addAdditionalProperty(GeneratorExternalConfigConstant.MODEL_PROJECT_NAME,
              contractFile.getParentFile().getName() + modelProjectNameSuffix)
          .addAdditionalProperty("apiName", contractFile.getName().split("\\.")[0])
          .addAdditionalProperty("microserviceName", contractFile.getParentFile().getName());

      configurator.setInputSpec(contractLocation);
      Objects.requireNonNull(codeGenerator)
          .configure(Collections.singletonMap("configurators", Collections.singletonList(configurator)));
      codeGenerator.generate();
    }
  }

  private static void commonConfig(CodegenConfigurator configurator, ServiceConfig service) {

    configurator
        .setGeneratorName(service.getMicroServiceFramework())
        .setGroupId(service.getGroupId())
        .setArtifactId(service.getArtifactId())
        .setModelPackage(service.getPackageName())
        .addAdditionalProperty("mainClassPackage", Optional.ofNullable(service.getPackageName()).orElse(""))
        .setArtifactVersion(service.getArtifactVersion())
        .addAdditionalProperty(ProjectMetaConstant.SERVICE_TYPE,
            Optional.ofNullable(service.getServiceType()).orElse("all"))
        .addAdditionalProperty(ProjectMetaConstant.SERVICE_ID, service.getServiceId());

    Optional.ofNullable(service.getProviderServiceId()).ifPresent(providerServiceId -> configurator
        .addAdditionalProperty(ProjectMetaConstant.PROVIDER_SERVICE_ID, service.getProviderServiceId()));

    if (MicroServiceFramework.SERVICECOMB.name().equalsIgnoreCase(service.getMicroServiceFramework())) {
      configurator.setLibrary(service.getProgrammingModel());
    }

    configurator.setApiPackage(
        Optional.ofNullable(service.getApiPackage()).orElse(String.format("%s.api", service.getPackageName())));
    configurator.setModelPackage(
        Optional.ofNullable(service.getModelPackage()).orElse(String.format("%s.model", service.getPackageName())));
  }
}
