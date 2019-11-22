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

package org.apache.servicecomb.toolkit.oasv.style.validator.paths;

import io.swagger.v3.oas.models.Paths;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathsValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isMatchCase;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathsObject">Paths Object</a>
 * key case validator
 * <ul>
 *   <li>config item: paths.key.case=upper-camel-case/lower-camel-case/upper-hyphen-case</li>
 *   <li>must match case</li>
 * </ul>
 * <p>
 *   <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#path-templating">Path Templating</a>
 *   variable must also match case
 * </p>
 */
public class PathsKeyCaseValidator implements PathsValidator {

  public static final String CONFIG_KEY = "paths.key.case";
  public static final String ERROR = "Must be ";

  private static final Pattern TEMPLATE_PATTERN = Pattern.compile("^\\{(.*)\\}$");

  private final String expectedCase;


  public PathsKeyCaseValidator(String expectedCase) {
    this.expectedCase = expectedCase;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, Paths oasObject) {
    List<OasViolation> violations = new ArrayList<>();

    Set<String> paths = oasObject.keySet();

    for (String path : paths) {
      if (!matchCamelCase(path)) {
        OasObjectPropertyLocation pathLoc = location.property(path, OasObjectType.PATH_ITEM);
        violations.add(new OasViolation(pathLoc, ERROR + expectedCase));
      }
    }

    return violations;
  }

  private boolean matchCamelCase(String path) {

    String[] pathSegments = path.split("/");

    for (String pathSegment : pathSegments) {
      if (StringUtils.isEmpty(pathSegment)) {
        continue;
      }
      String matchingPart = pathSegment;
      if (isTemplate(pathSegment)) {
        matchingPart = extractTemplateVariable(pathSegment);
      }
      if (!isMatchCase(expectedCase, matchingPart)) {
        return false;
      }
    }
    return true;

  }

  private boolean isTemplate(String pathSegment) {
    return TEMPLATE_PATTERN.matcher(pathSegment).matches();
  }

  private String extractTemplateVariable(String pathSegment) {
    Matcher matcher = TEMPLATE_PATTERN.matcher(pathSegment);
    if (matcher.matches()) {
      return matcher.group(1);
    }
    return "";
  }

}
