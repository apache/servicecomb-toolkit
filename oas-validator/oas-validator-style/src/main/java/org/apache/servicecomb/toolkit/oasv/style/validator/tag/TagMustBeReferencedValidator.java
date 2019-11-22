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

package org.apache.servicecomb.toolkit.oasv.style.validator.tag;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.TagValidator;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static java.util.Collections.emptySet;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#tagObject">Tag Object</a>
 * referencing validator
 * <ul>
 *   <li>config item: tag.name.must_be_referenced=*boolean*</li>
 *   <li>
 *   tag object must be referenced by <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#operationObject">Operation Object</a>
 *   </li>
 * </ul>
 */
public class TagMustBeReferencedValidator implements TagValidator {

  public static final String CONFIG_KEY = "tag.name.must_be_referenced";
  public static final String ERROR = "Must be referenced by at least one Operation object";

  private static final String CACHE_KEY = TagMustBeReferencedValidator.class.getName() + ".allOperationsTags";

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, Tag tag) {
    Set<String> allOperationsTags = getAllOperationsTags(context);

    List<OasViolation> violations = new ArrayList<>();

    if (!allOperationsTags.contains(tag.getName())) {
      violations.add(new OasViolation(location, ERROR));
    }

    return violations;

  }

  private Set<String> getAllOperationsTags(OasValidationContext context) {

    Set<String> allTags = context.getAttribute(CACHE_KEY);
    if (allTags != null) {
      return allTags;
    }

    allTags = new HashSet<>();

    OpenAPI openAPI = context.getOpenAPI();
    Paths paths = openAPI.getPaths();
    if (paths == null) {
      return emptySet();
    }

    for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
      PathItem pathItem = entry.getValue();
      List<Operation> operations = pathItem.readOperations();
      for (Operation operation : operations) {
        List<String> tags = operation.getTags();
        if (CollectionUtils.isEmpty(tags)) {
          continue;
        }
        allTags.addAll(tags);
      }
    }

    context.setAttribute(CACHE_KEY, allTags);
    return allTags;
  }
}
