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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.schema;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.SchemaValidator;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SCHEMA;
import static org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils.doValidateListProperty;
import static org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils.doValidateMapPropertyValues;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Template for recursively validate current Schema Object and Schema Object appears in its properties
 */
public abstract class SchemaRecursiveValidatorTemplate implements SchemaValidator {

  @Override
  public final List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    Schema oasObject) {

    if (StringUtils.isNotBlank(oasObject.get$ref())) {
      return emptyList();
    }
    if (oasObject instanceof ComposedSchema) {
      return validateComposedSchema(context, (ComposedSchema) oasObject, location);
    }
    if (oasObject instanceof ArraySchema) {
      return validateArraySchema(context, (ArraySchema) oasObject, location);
    }
    return validateOrdinarySchema(context, oasObject, location);

  }

  private List<OasViolation> validateOrdinarySchema(OasValidationContext context, Schema oasObject,
    OasObjectPropertyLocation location) {

    List<OasViolation> violations = new ArrayList<>();
    violations.addAll(validateCurrentSchemaObject(context, oasObject, location));

    violations.addAll(
      doValidateMapPropertyValues(
        context,
        location,
        "properties",
        oasObject.getProperties(),
        SCHEMA,
        singletonList(this)
      )
    );

    return violations;

  }

  private List<OasViolation> validateArraySchema(OasValidationContext context, ArraySchema oasObject,
    OasObjectPropertyLocation location) {
    return validate(context, location.property("items", SCHEMA), oasObject.getItems());
  }

  private List<OasViolation> validateComposedSchema(OasValidationContext context, ComposedSchema oasObject,
    OasObjectPropertyLocation location) {

    List<OasViolation> violations = new ArrayList<>();

    violations.addAll(
      doValidateListProperty(
        context, location,
        "allOf",
        oasObject.getAllOf(),
        SCHEMA,
        singletonList(this)
      )
    );

    violations.addAll(
      doValidateListProperty(
        context, location,
        "anyOf",
        oasObject.getAnyOf(),
        SCHEMA,
        singletonList(this)
      )
    );

    violations.addAll(
      doValidateListProperty(
        context, location,
        "oneOf",
        oasObject.getOneOf(),
        SCHEMA,
        singletonList(this)
      )
    );

    return violations;
  }

  protected abstract List<OasViolation> validateCurrentSchemaObject(OasValidationContext context, Schema oasObject,
    OasObjectPropertyLocation location);

}
