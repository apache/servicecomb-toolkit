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

package org.apache.servicecomb.toolkit.oasv.compatibility;

import static java.util.Collections.emptyList;

import java.util.List;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public abstract class CompatibilityCheckParser {

  private CompatibilityCheckParser() {
    // singleton
  }

  public static SwaggerParseResult parseYaml(String yaml) {
    OpenAPIV3Parser parser = new OpenAPIV3Parser();
    return parser.readContents(yaml, null, createParseOptions());
  }

  public static List<String> checkSyntax(String yaml) {
    SwaggerParseResult result = parseYaml(yaml);
    return result.getMessages() == null ? emptyList() : result.getMessages();
  }

  private static ParseOptions createParseOptions() {

    ParseOptions parseOptions = new ParseOptions();
    parseOptions.setResolve(true);
    parseOptions.setResolveCombinators(true);
    parseOptions.setResolveFully(true);
    parseOptions.setFlatten(false);
    return parseOptions;
  }
}
