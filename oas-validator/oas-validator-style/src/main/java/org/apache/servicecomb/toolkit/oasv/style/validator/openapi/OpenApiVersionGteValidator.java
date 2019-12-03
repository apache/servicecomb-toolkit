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

package org.apache.servicecomb.toolkit.oasv.style.validator.openapi;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.*;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#openapi-object">OpenAPI Object</a>
 * .openapi property validator
 <ul>
 *   <li>config item: openAPI.openapi.gte=expected</li>
 *   <li>version should be &gt;= *expected*</li>
 * </ul>
 */
public class OpenApiVersionGteValidator implements OpenApiValidator {

  public static final String CONFIG_KEY = "openAPI.openapi.gte";
  public static final String ERROR = "Must be >= ";

  private final String versionGte;

  public OpenApiVersionGteValidator(String versionGte) {
    this.versionGte = versionGte;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, OpenAPI openAPI) {

    if (openAPI.getOpenapi() == null) {
      return singletonList(new OasViolation(location.property("openapi"), ViolationMessages.REQUIRED));
    }

    String[] semver = openAPI.getOpenapi().split("\\.");
    String[] expectedSemver = versionGte.split("\\.");
    boolean ok = semver[0].equals(expectedSemver[0])
        && semver[1].equals(expectedSemver[1])
        && Integer.parseInt(semver[2]) >= Integer.valueOf(expectedSemver[2]);
    if (ok) {
      return emptyList();
    }
    return singletonList(new OasViolation(location.property("openapi"), ERROR + versionGte));
  }

}
