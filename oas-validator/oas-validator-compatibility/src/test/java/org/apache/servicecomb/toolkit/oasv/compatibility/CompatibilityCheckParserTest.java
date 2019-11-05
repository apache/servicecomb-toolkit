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


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.swagger.v3.parser.core.models.SwaggerParseResult;
import io.swagger.v3.parser.util.ClasspathHelper;

public class CompatibilityCheckParserTest {

  @Test
  public void parseYaml() {
    String yaml = loadRelative("parser-test.yaml");
    SwaggerParseResult swaggerParseResult = CompatibilityCheckParser.parseYaml(yaml);
    assertThat(swaggerParseResult).isNotNull();
    assertThat(swaggerParseResult.getOpenAPI()).isNotNull();
    assertThat(swaggerParseResult.getMessages()).isEmpty();
  }

  private String loadRelative(String filename) {
    String basePath = getClass().getPackage().getName().replaceAll("\\.", "/");
    return ClasspathHelper.loadFileFromClasspath(basePath + "/" + filename);
  }
}
