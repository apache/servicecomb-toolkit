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

package org.apache.servicecomb.toolkit.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.generator.parser.api.OpenApiAnnotationParser;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

public class OasContext {

  private OpenAPI openAPI;

  private String basePath;

  private Class<?> cls;

  private List<OperationContext> operationList = new ArrayList<>();

  private OpenApiAnnotationParser parser;

  public OasContext(OpenApiAnnotationParser parser) {
    this(new OpenAPI(), parser);
  }

  public OasContext(OpenAPI openAPI, OpenApiAnnotationParser parser) {
    this.openAPI = openAPI;
    this.parser = parser;
  }

  public OpenAPI toOpenAPI() {
    ensurePaths();
    for (OperationContext operationCtx : operationList) {
      if (!operationCtx.hasOperation()) {
        continue;
      }

      if (openAPI.getPaths() == null) {
        openAPI.setPaths(new Paths());
      }

      PathItem pathItem = openAPI.getPaths().get(operationCtx.getPath());
      if (pathItem == null) {
        pathItem = new PathItem();
        openAPI.path(operationCtx.getPath(), pathItem);
      }
      pathItem.operation(HttpMethod.valueOf(operationCtx.getHttpMethod()), operationCtx.toOperation());
    }

    // 如果没有restful资源则返回null
    if (openAPI.getPaths() == null || openAPI.getPaths().size() == 0) {
      return null;
    }

    openAPI.info(new Info().title("gen").version("1.0.0"));

    correctBasepath();
    correctComponents();

    openAPI.servers(Collections.singletonList(new Server().url(basePath)));

    return openAPI;
  }

  private void correctComponents() {
    Components nullComponents = new Components();
    if (nullComponents.equals(getComponents())) {
      openAPI.setComponents(null);
    }
  }

  private void correctBasepath() {
    if (StringUtils.isEmpty(basePath)) {
      basePath = "/";
    }

    if (!basePath.startsWith("/")) {
      basePath = "/" + basePath;
    }
  }

  public Components getComponents() {
    if (openAPI.getComponents() == null) {
      openAPI.setComponents(new Components());
    }
    return openAPI.getComponents();
  }

  private void ensurePaths() {
    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }
  }

  public OpenApiAnnotationParser getParser() {
    return parser;
  }

  public void setParser(OpenApiAnnotationParser parser) {
    this.parser = parser;
  }

  public OpenAPI getOpenAPI() {
    return openAPI;
  }

  public String getBasePath() {
    return basePath;
  }

  public Class<?> getCls() {
    return cls;
  }

  public void setCls(Class<?> cls) {
    this.cls = cls;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public void addOperation(OperationContext operation) {
    operationList.add(operation);
  }
}
