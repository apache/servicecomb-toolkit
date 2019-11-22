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

package org.apache.servicecomb.toolkit.oasv.style.validator.parameter;

import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isMatchCase;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#parameterObject">Parameter Object</a>
 * .name case validator for cookie
 * <ul>
 * <li>config item: parameter.name.cookie.case=upper-camel-case/lower-camel-case/upper-hyphen-case</li>
 * <li>must match case</li>
 * </ul>
 */
public class ParameterNameCookieCaseValidator implements ParameterValidator {

  public static final String CONFIG_KEY = "parameter.name.cookie.case";
  public static final String ERROR = "Must be ";

  private final String expectedCase;

  public ParameterNameCookieCaseValidator(String expectedCase) {
    this.expectedCase = expectedCase;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    Parameter oasObject) {

    if (StringUtils.isNotBlank(oasObject.get$ref())) {
      return emptyList();
    }

    if (!"cookie".equalsIgnoreCase(oasObject.getIn())) {
      return emptyList();
    }

    if (!isMatchCase(expectedCase, oasObject.getName())) {
      return singletonList(new OasViolation(location.property("name"), ERROR + expectedCase));
    }
    return emptyList();
  }
}
