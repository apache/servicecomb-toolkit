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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.api.TemplatingEngineAdapter;
import org.openapitools.codegen.templating.MustacheEngineAdapter;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.parser.OpenAPIParser;
import io.swagger.parser.SwaggerParser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class TemplateTest {

  String[] apiTemplateLocations = new String[] {
      "ServiceComb/libraries/JAX-RS/api.mustache",
      "ServiceComb/libraries/POJO/api.mustache",
      "ServiceComb/libraries/SpringBoot/api.mustache",
      "ServiceComb/libraries/SpringMVC/api.mustache",
      "ServiceComb/consumer/apiConsumer.mustache",
      "ServiceComb/consumer/pojo/apiConsumer.mustache"
  };

  @Test
  public void generateApisWithNoModel() throws IOException {
    ServiceCombCodegen config = new ServiceCombCodegen();

    Map<String, Object> templateData = readSwaggerModelInfo("no-model.yaml", config);

    assertNotNull(templateData);
    assertNull(templateData.get("imports"));

    templateData.putAll(config.additionalProperties());
    templateData.put("apiPackage", config.apiPackage());

    Arrays.stream(apiTemplateLocations).forEach(apiTemplateLocation -> {
      try {
        String renderResult = renderTemplate(templateData, apiTemplateLocation);
        assertNotNull(renderResult);
        assertFalse(renderResult.contains(config.modelPackage()));
      } catch (IOException e) {
        fail("Run 'generateApisWithNoModel' failed: " + e.getMessage());
      }
    });
  }

  @Test
  public void generateApisWithModel() throws IOException {
    ServiceCombCodegen config = new ServiceCombCodegen();
    TemplatingEngineAdapter templateEngine = new MustacheEngineAdapter();
    Map<String, Object> templateData = readSwaggerModelInfo("with-model.yaml", config);

    assertNotNull(templateData);
    assertNotNull(templateData.get("imports"));

    templateData.putAll(config.additionalProperties());
    templateData.put("apiPackage", config.apiPackage());

    Arrays.stream(apiTemplateLocations).forEach(apiTemplateLocation -> {
      try {
        String renderResult = renderTemplate(templateData, apiTemplateLocation);
        assertNotNull(renderResult);
        assertTrue(renderResult.contains(config.modelPackage()));
      } catch (IOException e) {
        fail("Run 'generateApisWithModel' failed: " + e.getMessage());
      }
    });
  }

  private Map<String, Object> readSwaggerModelInfo(String swaggerYamlFile, CodegenConfig config) throws IOException {

    Map<String, Object> templateData = new HashMap<>();
    String swaggerString = readResourceInClasspath(swaggerYamlFile);
    Swagger swagger = new SwaggerParser().parse(swaggerString);

    ParseOptions options = new ParseOptions();
    options.setResolve(true);
    options.setFlatten(true);
    SwaggerParseResult result = new OpenAPIParser().readContents(swaggerString, null, options);
    OpenAPI openAPI = result.getOpenAPI();

    Components components = openAPI.getComponents();

    Map<String, Model> definitions = swagger.getDefinitions();
    if (definitions == null) {
      return templateData;
    }
    List<Map<String, String>> imports = new ArrayList<Map<String, String>>();
    for (String key : components.getSchemas().keySet()) {
      Schema mm = components.getSchemas().get(key);
      CodegenModel cm = config.fromModel(key, mm);
      Map<String, String> item = new HashMap<String, String>();
      item.put("import", config.toModelImport(cm.classname));
      imports.add(item);
    }
    templateData.put("imports", imports);
    return templateData;
  }

  private String renderTemplate(Map<String, Object> templateData, String templateLocation) throws IOException {
    String templateString = readResourceInClasspath(templateLocation);
    Mustache.Compiler compiler = Mustache.compiler();
    Template tmpl = compiler.compile(templateString);
    return tmpl.execute(templateData);
  }

  private String readResourceInClasspath(String resourceName) throws IOException {
    InputStream resourceStream = TemplateTest.class.getClassLoader().getResourceAsStream(resourceName);
    if (resourceStream == null) {
      throw new IOException("resource " + resourceName + " not found");
    }
    StringBuilder stringBuilder = new StringBuilder();
    int len = -1;
    byte[] buffer = new byte[2048];
    while ((len = resourceStream.read(buffer)) != -1) {
      stringBuilder.append(new String(buffer, 0, len));
    }
    return stringBuilder.toString();
  }
}
