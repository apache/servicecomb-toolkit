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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.api;

import io.swagger.v3.oas.models.OpenAPI;

import java.util.HashMap;
import java.util.Map;

public class OasDiffValidationContext {

  private final OpenAPI leftOpenAPI;

  private final OpenAPI rightOpenAPI;

  private final Map<String, Object> attributes = new HashMap<>();

  public OasDiffValidationContext(OpenAPI leftOpenAPI, OpenAPI rightOpenAPI) {
    this.leftOpenAPI = leftOpenAPI;
    this.rightOpenAPI = rightOpenAPI;
  }

  public OpenAPI getLeftOpenAPI() {
    return leftOpenAPI;
  }

  public OpenAPI getRightOpenAPI() {
    return rightOpenAPI;
  }

  public <T> T getAttribute(String name) {
    return (T) attributes.get(name);
  }

  public <T> void setAttribute(String name, T attr) {
    this.attributes.put(name, attr);
  }

  public void removeAttribute(String name) {
    this.attributes.remove(name);
  }

}
