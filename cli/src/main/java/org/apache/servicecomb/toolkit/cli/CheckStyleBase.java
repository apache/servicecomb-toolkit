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

package org.apache.servicecomb.toolkit.cli;

import com.google.common.base.Charsets;
import io.airlift.airline.Option;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.servicecomb.toolkit.oasv.FactoryOptions;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.style.StyleCheckParser;
import org.apache.servicecomb.toolkit.oasv.style.factory.ValidatorFactoryComponents;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasSpecValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OasSpecValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class CheckStyleBase implements Runnable {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Option(name = { "-r", "--rules-file" }, title = "rules properties file", required = true,
      description = "rules properties file")
  private String rulesFile;

  @Option(name = { "-f", "--file" }, title = "OpenAPI v3 spec yaml", required = true,
      description = "OpenAPI v3 spec yaml")
  private String filePath;

  @Override
  public void run() {

    FactoryOptions factoryOptions;
    try {
      factoryOptions = loadFactoryOptions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String yaml = null;
    try {
      yaml = loadFileContent(filePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    SwaggerParseResult parseResult = StyleCheckParser.parseYaml(yaml);
    OpenAPI openAPI = parseResult.getOpenAPI();
    if (openAPI == null) {
      StringJoiner sj = new StringJoiner("\n");
      if (CollectionUtils.isNotEmpty(parseResult.getMessages())) {
        for (String message : parseResult.getMessages()) {
          sj.add(message);
        }
      }
      throw new RuntimeException(sj.toString());
    }

    OasSpecValidator oasSpecValidator = createOasSpecValidator(factoryOptions);

    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    if (CollectionUtils.isNotEmpty(violations)) {
      for (OasViolation violation : violations) {
        LOGGER.info("path  : {}\nerror : {}\n------",
            OasObjectPropertyLocation.toPathString(violation.getLocation()), violation.getError());
      }
      throw new ValidationFailedException("check not passed");
    }
    LOGGER.info("Everything is good");
  }

  private OasSpecValidator createOasSpecValidator(FactoryOptions factoryOptions) {

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
        ValidatorFactoryComponents.class);
    try {
      OasSpecValidatorFactory oasSpecValidatorFactory = ctx.getBean(OasSpecValidatorFactory.class);
      return oasSpecValidatorFactory.create(factoryOptions);
    } finally {
      ctx.close();
    }
  }


  private String loadFileContent(String filePath) throws IOException {
    Path specPath = Paths.get(filePath);
    return FileUtils.readFileToString(specPath.toFile(), Charsets.UTF_8);
  }

  private OasValidationContext createContext(OpenAPI openAPI) {

    OasValidationContext oasValidationContext = new OasValidationContext(openAPI);
    return oasValidationContext;
  }

  private FactoryOptions loadFactoryOptions() throws IOException {
    Path specPath = Paths.get(rulesFile);
    specPath.toAbsolutePath().toString();
    Properties properties = new Properties();
    try (FileInputStream fis = new FileInputStream(specPath.toFile())) {
      properties.load(fis);
    }
    return new FactoryOptions(properties);
  }
}
