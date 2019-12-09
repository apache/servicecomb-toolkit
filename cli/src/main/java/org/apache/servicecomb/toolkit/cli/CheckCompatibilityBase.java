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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.compatibility.CompatibilityCheckParser;
import org.apache.servicecomb.toolkit.oasv.compatibility.factory.DefaultOasSpecDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasSpecDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.OasSpecDiffValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.airlift.airline.Arguments;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class CheckCompatibilityBase implements Runnable {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Arguments(
      title = "files", required = true,
      description = "Two OpenAPI v3 spec yamls"
  )
  private List<String> filePaths;

  @Override
  public void run() {

    if (filePaths.size() != 2) {
      LOGGER.error("Require 2 files");
      return;
    }

    OpenAPI oldOas = null;
    OpenAPI newOas = null;
    try {
      oldOas = loadOpenApi(filePaths.get(0));
      newOas = loadOpenApi(filePaths.get(1));
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      return;
    }

    OasSpecDiffValidator diffValidator = createOasSpecDiffValidator();

    List<OasDiffViolation> violations = diffValidator.validate(createContext(oldOas, newOas), oldOas, newOas);

    if (CollectionUtils.isNotEmpty(violations)) {
      for (OasDiffViolation violation : violations) {
        LOGGER.info("left  : {}\nright : {}\nerror : {}\n------",
            OasObjectPropertyLocation.toPathString(violation.getLeftLocation()),
            OasObjectPropertyLocation.toPathString(violation.getRightLocation()),
            violation.getError()
        );
      }
      throw new ValidationFailedException("check not passed");
    }
    LOGGER.info("Everything is good");
  }

  private OpenAPI loadOpenApi(String filePath) throws IOException {
    String yaml = loadFileContent(filePath);
    SwaggerParseResult oldParseResult = CompatibilityCheckParser.parseYaml(yaml);
    OpenAPI openAPI = oldParseResult.getOpenAPI();
    if (openAPI == null) {
      StringJoiner errors = new StringJoiner("\n", "Parse errors:", "");
      if (CollectionUtils.isNotEmpty(oldParseResult.getMessages())) {
        for (String message : oldParseResult.getMessages()) {
          errors.add(message);
        }
      }
      throw new RuntimeException(errors.toString());
    }

    return openAPI;
  }


  private OasSpecDiffValidator createOasSpecDiffValidator() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
        DefaultOasSpecDiffValidatorFactory.class.getPackage().getName());
    try {
      OasSpecDiffValidatorFactory diffValidatorFactory = ctx.getBean(OasSpecDiffValidatorFactory.class);
      return diffValidatorFactory.create();
    } finally {
      ctx.close();
    }
  }


  private String loadFileContent(String filePath) throws IOException {
    Path specPath = Paths.get(filePath);
    specPath.toAbsolutePath().toString();
    return FileUtils.readFileToString(specPath.toFile());
  }

  private OasDiffValidationContext createContext(OpenAPI leftOpenAPI, OpenAPI rightOpenAPI) {

    OasDiffValidationContext context = new OasDiffValidationContext(leftOpenAPI, rightOpenAPI);
    return context;
  }
}
