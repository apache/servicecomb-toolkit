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

import io.swagger.v3.oas.models.OpenAPI;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SECURITY_REQUIREMENT;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#openapi-object">OpenAPI Object</a>
 * .security property validator
 * <ul>
 * <li>config item: openAPI.security.size.eq=expected</li>
 * <li>size should be equals to *expected*</li>
 * </ul>
 */
public class OpenApiSecuritySizeEqValidator implements OpenApiValidator {

  public static final String CONFIG_KEY = "openAPI.security.size.eq";
  public static final String ERROR = "size must be == ";

  private final int expectedSize;

  public OpenApiSecuritySizeEqValidator(int expectedSize) {
    this.expectedSize = expectedSize;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    OpenAPI openAPI) {

    int size = CollectionUtils.size(openAPI.getSecurity());
    if (size != expectedSize) {
      return singletonList(
          new OasViolation(location.property("security", SECURITY_REQUIREMENT), ERROR + expectedSize));
    }

    return emptyList();

  }

}
