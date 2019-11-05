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

package org.apache.servicecomb.toolkit.oasv.web.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.oasv.compatibility.CompatibilityCheckParser;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasSpecDiffValidator;
import org.apache.servicecomb.toolkit.oasv.util.SyntaxChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

@RestController
@RequestMapping("/api/compatibility")
public class CompatibilityController {

  @Autowired
  private OasSpecDiffValidator oasSpecDiffValidator;

  @PostMapping(consumes = MimeTypeUtils.TEXT_PLAIN_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public Map<String, Object> validateOpenAPI(@RequestBody String yaml) {
    
    Map<String, Object> json = new HashMap<>();
    
    json.put("acknowleged", true);
    json.put("data", doValidate(yaml));
    
    return json;
  }


  private ImportError2 doValidate(String yaml) {

    ImportError2 importError = new ImportError2();

    String leftYaml = yaml.split("---\n")[0];
    String rightYaml = yaml.split("---\n")[1];

    importError.addLeftParseErrors(SyntaxChecker.check(leftYaml));
    importError.addRightParseErrors(SyntaxChecker.check(rightYaml));

    if (importError.isNotEmpty()) {
      return importError;
    }

    OpenAPI leftOpenAPI = loadByYaml(leftYaml);
    OpenAPI rightOpenAPI = loadByYaml(rightYaml);

    List<OasDiffViolation> violations = oasSpecDiffValidator
        .validate(createContext(leftOpenAPI, rightOpenAPI), leftOpenAPI, rightOpenAPI);
    if (CollectionUtils.isNotEmpty(violations)) {
      importError.addViolations(violations);
    }
    return importError;
  }

  private OpenAPI loadByYaml(String yaml) {
    SwaggerParseResult parseResult = CompatibilityCheckParser.parseYaml(yaml);
    if (CollectionUtils.isNotEmpty(parseResult.getMessages())) {
      throw new RuntimeException(StringUtils.join(parseResult.getMessages(), ","));
    }
    return parseResult.getOpenAPI();
  }


  
  private OasDiffValidationContext createContext(OpenAPI leftOpenAPI, OpenAPI rightOpenAPI) {

    OasDiffValidationContext context = new OasDiffValidationContext(leftOpenAPI, rightOpenAPI);
    initContext(context);
    return context;

  }
  
  
  private void initContext(OasDiffValidationContext context) {
  }

}
