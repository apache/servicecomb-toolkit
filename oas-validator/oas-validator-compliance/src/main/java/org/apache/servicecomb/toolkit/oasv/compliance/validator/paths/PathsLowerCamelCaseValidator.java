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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.paths;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import io.swagger.v3.oas.models.Paths;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isLowerCamelCase;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathsObject">Paths Object</a>lower camel case校验器
 * <p>
 *   <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#path-templating">Path Templating</a>的变量也要是lower camel case的
 * </p>
 */
public class PathsLowerCamelCaseValidator implements PathsValidator {

  private static final Pattern TEMPLATE_PATTERN = Pattern.compile("^\\{(.*)\\}$");

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, Paths oasObject) {
    List<OasViolation> violations = new ArrayList<>();

    Set<String> paths = oasObject.keySet();

    for (String path : paths) {
      if (!matchCamelCase(path)) {
        OasObjectPropertyLocation pathLoc = location.property(path, OasObjectType.PATH_ITEM);
        violations.add(new OasViolation(pathLoc, ViolationMessages.LOWER_CAMEL_CASE));
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
      if (!isLowerCamelCase(matchingPart)) {
        return false;
      }
    }
    return true;

  }

  /**
   *
   *
   * @param pathSegment
   * @return
   */
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
