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

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.oasv.FactoryOptions;
import org.apache.servicecomb.toolkit.oasv.style.StyleCheckParser;
import org.apache.servicecomb.toolkit.oasv.util.SyntaxChecker;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasSpecValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OasSpecValidatorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
@RequestMapping("/api/style")
public class StyleController implements InitializingBean {

  @Autowired
  private OasSpecValidatorFactory oasSpecValidatorFactory;

  private FactoryOptions factoryOptions;

  @PostMapping(consumes = MimeTypeUtils.TEXT_PLAIN_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public Map<String, Object> validateOpenAPI(@RequestBody String yaml) {

    ImportError importError = doValidate(yaml);
    Map<String, Object> json = new HashMap<>();
    
    json.put("acknowleged", true);
    json.put("data", importError);
    
    return json;
  }


  private ImportError doValidate(String yaml) {

    ImportError importError = new ImportError();
    importError.addParseErrors(SyntaxChecker.check(yaml));
    if (importError.isNotEmpty()) {
      return importError;
    }

    SwaggerParseResult parseResult = StyleCheckParser.parseYaml(yaml);
    if (CollectionUtils.isNotEmpty(parseResult.getMessages())) {
      throw new RuntimeException(StringUtils.join(parseResult.getMessages(), ","));
    }

    OpenAPI openAPI = loadByYaml(yaml);
    OasSpecValidator oasSpecValidator = oasSpecValidatorFactory.create(factoryOptions);
    List<OasViolation> violations = oasSpecValidator.validate(createContext(openAPI), openAPI);
    if (CollectionUtils.isNotEmpty(violations)) {
      importError.addViolations(violations);
    }

    return importError;
  }

  private OpenAPI loadByYaml(String yaml) {
    SwaggerParseResult parseResult = StyleCheckParser.parseYaml(yaml);
    if (CollectionUtils.isNotEmpty(parseResult.getMessages())) {
      throw new RuntimeException(StringUtils.join(parseResult.getMessages(), ","));
    }
    return parseResult.getOpenAPI();
  }
  
  private OasValidationContext createContext(OpenAPI openAPI) {

    OasValidationContext oasValidationContext = new OasValidationContext(openAPI);
    initContext(oasValidationContext);
    return oasValidationContext;

  }
  
  
  private void initContext(OasValidationContext context) {
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    try (InputStream inputStream
        = resourceResolver.getResource("classpath:style-check-rules.properties").getInputStream()) {
      Properties properties = new Properties();
      properties.load(inputStream);
      this.factoryOptions = new FactoryOptions(properties);
    }
  }
}
