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

package org.apache.servicecomb.toolkit.oasv;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class OasSpecLoader {

  /**
   * Parse file at absolute path
   *
   * @param classpath
   * @return
   */
  protected abstract SwaggerParseResult parseAbsolute(String classpath);


  /**
   * Load file at absolute path
   *
   * @param classpath
   * @return
   */
  protected final OpenAPI loadAbsolute(String classpath) {
    SwaggerParseResult parseResult = parseAbsolute(classpath);
    if (CollectionUtils.isNotEmpty(parseResult.getMessages())) {
      throw new RuntimeException(StringUtils.join(parseResult.getMessages(), ","));
    }
    return parseResult.getOpenAPI();
  }

  /**
   * Load file at relative path
   *
   * @param fileName
   * @return
   */
  protected final OpenAPI loadRelative(String fileName) {
    String basePath = getClass().getPackage().getName().replaceAll("\\.", "/");
    return loadAbsolute(basePath + "/" + fileName);
  }

}
