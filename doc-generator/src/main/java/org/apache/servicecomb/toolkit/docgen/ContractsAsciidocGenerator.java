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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Ordering;

import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;


public class ContractsAsciidocGenerator implements DocGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContractsAsciidocGenerator.class);

  private final static String DOC_FORMAT = "asciidoc-html";

  private final static String DOC_SUFFIX = ".html";

  @Override
  public boolean canProcess(String type) {
    return type != null && DOC_FORMAT.equals(type.toLowerCase());
  }

  @Override
  public String generate(Swagger contractContent, String outputPath) {

    Asciidoctor asciidoctor = Asciidoctor.Factory.create();
    Swagger2MarkupConverter.Builder markupBuilder = Swagger2MarkupConverter.from(contractContent);
    Swagger2MarkupConfigBuilder swagger2MarkupConfigBuilder = new Swagger2MarkupConfigBuilder()
        .withParameterOrdering(Ordering
            .explicit("path", "query", "header", "cookie", "formData", "body")
            .onResultOf(Parameter::getIn));
    String markup = markupBuilder.withConfig(swagger2MarkupConfigBuilder.build()).build().toString();

    final Map<String, Object> optionsMap = OptionsBuilder.options()
        .docType("book")
        .backend("html5")
        .headerFooter(true)
        .safe(SafeMode.UNSAFE)
        .attributes(AttributesBuilder.attributes()
            .attribute("toclevels", 3)
            .attribute(Attributes.TOC_2, true)
            .attribute(Attributes.TOC_POSITION, "left")
            .attribute(Attributes.LINK_CSS, true)
            .attribute(Attributes.SECTION_NUMBERS, true)
            .attribute(Attributes.SECT_NUM_LEVELS, 4))
        .asMap();
    String asciidocResult = asciidoctor.convert(markup, optionsMap);
    try {
      outputPath = correctPath(outputPath);
      Files.write(Paths.get(outputPath), asciidocResult.getBytes());
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }

    return asciidocResult;
  }

  private String correctPath(String filepath) {

    if (!filepath.endsWith(DOC_SUFFIX)) {
      return filepath + "-" + DOC_FORMAT + DOC_SUFFIX;
    }

    return filepath;
  }
}
