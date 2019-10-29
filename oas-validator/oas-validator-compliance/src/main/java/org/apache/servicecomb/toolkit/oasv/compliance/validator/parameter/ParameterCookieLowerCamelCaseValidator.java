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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.parameter;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils;
import org.apache.servicecomb.toolkit.oasv.validation.api.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#parameterObject">Parameter Object</a>
 * .name 属性校验器
 * <ul>
 * <li>如果in=cookie，必须lower camel case</li>
 * </ul>
 */
public class ParameterCookieLowerCamelCaseValidator implements ParameterValidator {
  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    Parameter oasObject) {

    if (StringUtils.isNotBlank(oasObject.get$ref())) {
      return emptyList();
    }

    if (!"cookie".equalsIgnoreCase(oasObject.getIn())) {
      return emptyList();
    }

    if (!StringCaseUtils.isLowerCamelCase(oasObject.getName())) {
      return singletonList(new OasViolation(location.property("name"), ViolationMessages.LOWER_CAMEL_CASE));
    }

    return emptyList();
  }
}
