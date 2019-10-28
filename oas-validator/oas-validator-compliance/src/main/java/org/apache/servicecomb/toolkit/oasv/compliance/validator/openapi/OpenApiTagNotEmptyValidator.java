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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import io.swagger.v3.oas.models.OpenAPI;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#openapi-object">OpenAPI Object</a>
 * .tags(List of <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#tagObject">Tag Object</a>)大小校验器
 * <ul>
 *   <li>数量至少为一</li>
 * </ul>
 */
public class OpenApiTagNotEmptyValidator implements OpenApiValidator {

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, OpenAPI openAPI) {

    if (CollectionUtils.isEmpty(openAPI.getTags())) {
      return singletonList(new OasViolation(location.property("tags", OasObjectType.TAG), "至少提供一个"));

    }
    return emptyList();

  }

}
