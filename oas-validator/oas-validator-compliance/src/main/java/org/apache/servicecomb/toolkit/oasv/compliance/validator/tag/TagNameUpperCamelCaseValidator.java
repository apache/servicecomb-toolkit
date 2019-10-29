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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.tag;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.*;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isUpperCamelCase;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#tagObject">Tag Object</a>
 * .name属性校验器
 * <ul>
 * <li>upper camel case</li>
 * </ul>
 */
public class TagNameUpperCamelCaseValidator implements TagValidator {

  @Override
  public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location, Tag tag) {

    if (!isUpperCamelCase(tag.getName())) {
      return singletonList(new OasViolation(location.property("name"), ViolationMessages.UPPER_CAMEL_CASE));
    }
    return emptyList();
  }

}
