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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.schema;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectProperty;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.*;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.schema.SchemaRecursiveValidatorTemplate;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.COMPONENTS;
import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SCHEMA;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#schemaObject">Schema Object</a>.title属性校验器
 * <ul>
 * <li>如果Schema Object的上级是
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#schemaObject">Schema Object</a>
 * /
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#componentsObject">Components Object</a>
 * ，那么必须写title</li>
 * </ul>
 */
public class SchemaTitleValidator extends SchemaRecursiveValidatorTemplate {

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
