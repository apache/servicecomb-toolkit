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

import io.airlift.airline.Arguments;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.compliance.ComplianceCheckParser;
import org.apache.servicecomb.toolkit.oasv.compliance.factory.ValidatorFactoryComponents;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasSpecValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OasSpecValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CheckStyleBase implements Runnable {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Arguments(
      title = "file", required = true,
      description = "OpenAPI v3 spec yaml"
  )
  private String filePath;

  @Override
  public void run() {

    String yaml = null;
    try {
      yaml = loadFileContent(filePath);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      return;
    }

    SwaggerParseResult parseResult = ComplianceCheckParser.parseYaml(yaml);
    OpenAPI openAPI = parseResult.getOpenAPI();
    if (openAPI == null) {
      if (CollectionUtils.isNotEmpty(parseResult.getMessages())) {
        for (String message : parseResult.getMessages()) {
          LOGGER.error(message);
        }
      }
      LOGGER.error("Parse error");
      return;
    }

    OasSpecValidator oasSpecValidator = createOasSpecValidator();

    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    if (CollectionUtils.isNotEmpty(violations)) {
      for (OasViolation violation : violations) {
        LOGGER.info("path  : {}\nerror : {}\n------",
            OasObjectPropertyLocation.toPathString(violation.getLocation()), violation.getError());
      }
      return;
    }
    LOGGER.info("Everything is good");
  }


  private OasSpecValidator createOasSpecValidator() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
        ValidatorFactoryComponents.class);
    try {
      OasSpecValidatorFactory oasSpecValidatorFactory = ctx.getBean(OasSpecValidatorFactory.class);
      // TODO load options
      return oasSpecValidatorFactory.create(null);
    } finally {
      ctx.close();
    }
  }


  private String loadFileContent(String filePath) throws IOException {
    Path specPath = Paths.get(filePath);
    specPath.toAbsolutePath().toString();
    return FileUtils.readFileToString(specPath.toFile());
  }

  private OasValidationContext createContext(OpenAPI openAPI) {

    OasValidationContext oasValidationContext = new OasValidationContext(openAPI);
    return oasValidationContext;
  }
}
