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

import io.swagger.v3.oas.models.tags.Tag;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.TagValidator;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isMatchCase;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#tagObject">Tag Object</a>
 * .name case validator
 * <ul>
 *   <li>config item: tag.name.case=upper-camel-case/lower-camel-case/upper-hyphen-case</li>
 *   <li>must match case</li>
 * </ul>
 */
public class TagNameCaseValidator implements TagValidator {

  public static final String CONFIG_KEY = "tag.name.case";
  public static final String ERROR = "Must be ";

  private final String expectedCase;

  public TagNameCaseValidator(String expectedCase) {
    this.expectedCase = expectedCase;
  }

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, Tag tag) {

    if (!isMatchCase(expectedCase, tag.getName())) {
      return singletonList(new OasViolation(location.property("name"), ERROR + expectedCase));
    }
    return emptyList();
  }

}
