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

package org.apache.servicecomb.toolkit.contractgen;

import static org.apache.servicecomb.toolkit.common.ContractFileType.YAML;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.SwaggerUtils;
import org.apache.servicecomb.swagger.generator.core.CompositeSwaggerGeneratorContext;
import org.apache.servicecomb.swagger.generator.core.SwaggerGenerator;
import org.apache.servicecomb.swagger.generator.core.SwaggerGeneratorContext;
import org.apache.servicecomb.toolkit.ContractsGenerator;
import org.apache.servicecomb.toolkit.common.ContractFileType;
import org.apache.servicecomb.toolkit.common.ImmediateClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class DefaultContractsGenerator implements ContractsGenerator {

  private static Logger LOGGER = LoggerFactory.getLogger(DefaultContractsGenerator.class);

  private static CompositeSwaggerGeneratorContext compositeSwaggerGeneratorContext = new CompositeSwaggerGeneratorContext();

  private Map<String, Object> config;

  private List<String> classpathUrls;

  private String outputDir = ".";

  private ContractFileType contractfileType = YAML;

  @Override
  public boolean canProcess(String type) {
    return "default".equals(type);
  }

  @Override
  public void configure(Map<String, Object> config) {
    this.config = config;

    if (config == null) {
      return;
    }

    Object classpathUrlsObj = config.get("classpathUrls");
    if (classpathUrlsObj instanceof List) {
      //noinspection unchecked
      classpathUrls = (List<String>) classpathUrlsObj;
    }

    Object outputDirObj = config.get("outputDir");
    if (outputDirObj instanceof String) {
      outputDir = (String) outputDirObj;
    }

    Object contractFileTypeObj = config.get("contractFileType");
    if (contractFileTypeObj instanceof String) {
      contractfileType = ContractFileType.getValue((String) contractFileTypeObj);
    }
  }

  @Override
  public boolean generate() throws RuntimeException {

    URL[] runtimeUrls = new URL[classpathUrls.size()];
    for (int i = 0; i < classpathUrls.size(); i++) {

      String element = classpathUrls.get(i);

      try {
        runtimeUrls[i] = new File(element).toURI().toURL();
      } catch (MalformedURLException e) {
        throw new RuntimeException("wrong element in classpath", e);
      }
    }

    if (!checkConfig()) {
      return false;
    }

    ImmediateClassLoader immediateClassLoader = new ImmediateClassLoader(runtimeUrls,
        Thread.currentThread().getContextClassLoader());

    try {

      Vector allClass = getAllClass(immediateClassLoader);

      for (int i = 0; i < allClass.size(); i++) {

        Class loadClass = (Class) allClass.get(i);
        if (!canProcess(loadClass)) {
          continue;
        }

        SwaggerGeneratorContext generatorContext =
            compositeSwaggerGeneratorContext.selectContext(loadClass);

        SwaggerGenerator generator = new SwaggerGenerator(generatorContext, loadClass);

        String swaggerString = SwaggerUtils.swaggerToString(generator.generate());

        File outputFile = new File(
            outputDir + File.separator + loadClass.getSimpleName() + contractfileType.getFileSuffix());

        if (!outputFile.exists()) {
          if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
          }
          outputFile.createNewFile();
        }

        Files.write(Paths.get(outputFile.toURI()), swaggerString.getBytes());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private boolean checkConfig() {
    return config != null && classpathUrls != null;
  }

  private static boolean canProcess(Class<?> loadClass) {

    if (loadClass == null) {
      return false;
    }
    RestSchema restSchema = loadClass.getAnnotation(RestSchema.class);
    if (restSchema != null) {
      return true;
    }

    RestController controller = loadClass.getAnnotation(RestController.class);
    if (controller != null) {
      return true;
    }

    RpcSchema rpcSchema = loadClass.getAnnotation(RpcSchema.class);
    if (rpcSchema != null) {
      return true;
    }

    RequestMapping requestMapping = loadClass.getAnnotation(RequestMapping.class);
    if (requestMapping != null) {
      return true;
    }

    return false;
  }

  private static Vector getAllClass(ClassLoader classLoader) {
    Field classesField;
    try {
      classesField = ClassLoader.class.getDeclaredField("classes");
      classesField.setAccessible(true);

      if (classesField.get(classLoader) instanceof Vector) {
        return (Vector) classesField.get(classLoader);
      }
    } catch (Exception e) {
      throw new RuntimeException("cannot get class from ClassLoader " + classLoader.getClass());
    }
    return new Vector<>();
  }
}
