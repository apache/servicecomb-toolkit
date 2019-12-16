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

package org.apache.servicecomb.toolkit.docgen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.servicecomb.toolkit.DocGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;

public class ContractsSwaggerUIGenerator implements DocGenerator {

  private final static Logger LOGGER = LoggerFactory.getLogger(ContractsSwaggerUIGenerator.class);

  private static Pattern variablePattern = Pattern.compile("(?<=\\{\\{)[a-zA-Z0-9_-]*(?=\\}\\})");

  private final static String DOC_SUFFIX = ".html";

  private OpenAPI contractContent;

  private String outputPath = ".";

  @Override

  public boolean canProcess(String type) {
    return "default".equals(type) || "swagger-ui".equals(type);
  }

  @Override
  public void configure(Map<String, Object> config) {
    this.contractContent = (OpenAPI) config.get("contractContent");
    this.outputPath = (String) config.get("outputPath");
  }

  private boolean checkConfig() {
    if (contractContent == null) {
      return false;
    }
    return true;
  }

  @Override
  public void generate() {

    if (!checkConfig()) {
      throw new IllegalArgumentException("Cannot found configuration");
    }

    String swaggerUiHtml = null;
    try {
      String swaggerJson = Json.mapper().writeValueAsString(contractContent);
      swaggerUiHtml = renderHtml(getSwaggerUiHtml(), Collections.singletonMap("spec", swaggerJson));

      outputPath = correctPath(outputPath);
      Path outputFile = Paths.get(outputPath);

      if (!Files.exists(outputFile)) {
        if (outputFile.getParent() != null) {
          Files.createDirectories(outputFile.getParent());
        }
        Files.createFile(outputFile);
      }

      Files.write(outputFile, swaggerUiHtml.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String correctPath(String filepath) {

    if (!filepath.endsWith(DOC_SUFFIX)) {
      return filepath + DOC_SUFFIX;
    }

    return filepath;
  }

  private String renderHtml(String html, Map<String, String> args) {

    Matcher variableMatcher = variablePattern.matcher(html);
    while (variableMatcher.find()) {
      String variableStr = variableMatcher.group();
      String variableValue = args.get(variableStr);

      html = html.replace("{{" + variableStr + "}}", variableValue);
    }

    return html;
  }

  private String getSwaggerUiHtml() throws IOException {
    int len;

    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("webroot/swagger-ui.html");

    byte[] buf = new byte[1024];
    while ((len = in.read(buf)) != -1) {
      bout.write(buf, 0, len);
    }
    bout.close();
    in.close();

    return new String(bout.toByteArray());
  }
}
