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

package org.apache.servicecomb.toolkit.oasv.style.validator.schema;

import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectProperty;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.schema.SchemaRecursiveValidatorTemplate;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.COMPONENTS;
import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SCHEMA;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#schemaObject">Schema Object</a>
 * .title property required validator
 * <ul>
 *   <li>config item: schema.title.required=*boolean*</li>
 *   <li>
 *   if this Schema Object's parent is
 *   <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#schemaObject">Schema Object</a>
 *   /
 *   <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#componentsObject">Components Object</a>
 *  , then title property is required
 *  </li>
 * </ul>
 */
public class SchemaTitleRequiredValidator extends SchemaRecursiveValidatorTemplate {

  public static final String CONFIG_KEY = "schema.title.required";

  @Override
  protected List<OasViolation> validateCurrentSchemaObject(OasValidationContext context, Schema oasObject,
    OasObjectPropertyLocation location) {

    if (StringUtils.isNotBlank(oasObject.get$ref())) {
      return emptyList();
    }

    OasObjectProperty parentProperty = location.getParent();

    if (SCHEMA == parentProperty.getObjectType()
        || COMPONENTS == parentProperty.getObjectType()
    ) {
      return check(oasObject, location);
    }

    return emptyList();
  }

  private List<OasViolation> check(Schema oasObject, OasObjectPropertyLocation location) {
    if (StringUtils.isNotBlank(oasObject.getTitle())) {
      return emptyList();
    }
    return singletonList(
      new OasViolation(location.property("title", null), ViolationMessages.REQUIRED));
  }
}
