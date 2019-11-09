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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.schema;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.*;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasObjectDiffValidatorUtils.*;
import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SCHEMA;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Schema object difference validator engine.
 * It does validation recursively
 */
public class SchemaDiffValidatorEngine
  extends OasObjectDiffValidatorTemplate<Schema>
  implements SchemaDiffValidator {

  private final List<SchemaAddValidator> schemaAddValidators;
  private final List<SchemaDelValidator> schemaDelValidators;
  private final List<SchemaCompareValidator> schemaCompareValidators;

  public SchemaDiffValidatorEngine(
    List<SchemaAddValidator> schemaAddValidators,
    List<SchemaDelValidator> schemaDelValidators,
    List<SchemaCompareValidator> schemaCompareValidators) {
    this.schemaAddValidators = new ArrayList<>(schemaAddValidators);
    this.schemaDelValidators = new ArrayList<>(schemaDelValidators);
    this.schemaCompareValidators = new ArrayList<>(schemaCompareValidators);
  }

  @Override
  protected List<OasDiffViolation> validateAdd(OasDiffValidationContext context, OasObjectPropertyLocation rightLocation,
    Schema rightOasObject) {

    return schemaAddValidators
      .stream()
      .map(v -> v.validate(context, rightLocation, rightOasObject))
      .flatMap(list -> list.stream())
      .collect(toList());

  }

  @Override
  protected List<OasDiffViolation> validateDel(OasDiffValidationContext context, OasObjectPropertyLocation leftLocation,
    Schema leftOasObject) {

    return schemaDelValidators
      .stream()
      .map(v -> v.validate(context, leftLocation, leftOasObject))
      .flatMap(list -> list.stream())
      .collect(toList());

  }

  @Override
  protected List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Schema leftOasObject,
    OasObjectPropertyLocation rightLocation, Schema rightOasObject) {

    List<OasDiffViolation> violations = new ArrayList<>();
    violations.addAll(
      schemaCompareValidators
        .stream()
        .map(v -> v.validate(context, leftLocation, leftOasObject, rightLocation, rightOasObject))
        .flatMap(list -> list.stream())
        .collect(toList())
    );

    violations.addAll(
      validateCompareOrdinary(context, leftLocation, leftOasObject, rightLocation, rightOasObject)
    );
    violations.addAll(
      validateCompareArray(context, leftLocation, leftOasObject, rightLocation, rightOasObject)
    );
    violations.addAll(
      validateCompareComposed(context, leftLocation, leftOasObject, rightLocation, rightOasObject)
    );
    return violations;

  }

  private List<OasDiffViolation> validateCompareOrdinary(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Schema leftOasObject,
    OasObjectPropertyLocation rightLocation, Schema rightOasObject) {

    return doDiffValidateMapProperty(
      context,
      "properties",
      leftLocation,
      leftOasObject.getProperties(),
      rightLocation,
      rightOasObject.getProperties(),
      SCHEMA,
      singletonList(this)
    );

  }

  private List<OasDiffViolation> validateCompareArray(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Schema leftOasObject,
    OasObjectPropertyLocation rightLocation, Schema rightOasObject) {

    Schema leftItems = null;
    OasObjectPropertyLocation leftItemsLocation = null;
    if (leftOasObject instanceof ArraySchema) {
      leftItems = ((ArraySchema) leftOasObject).getItems();
      leftItemsLocation = leftLocation.property("items", SCHEMA);
    }

    Schema<?> rightItems = null;
    OasObjectPropertyLocation rightItemsLocation = null;
    if (rightOasObject instanceof ArraySchema) {
      rightItems = ((ArraySchema) rightOasObject).getItems();
      rightItemsLocation = rightLocation.property("items", SCHEMA);
    }

    return doDiffValidateProperty(
      context,
      leftItemsLocation,
      leftItems,
      rightItemsLocation,
      rightItems,
      singletonList(this)
    );

  }

  private List<OasDiffViolation> validateCompareComposed(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Schema leftOasObject,
    OasObjectPropertyLocation rightLocation, Schema rightOasObject) {

    List<OasDiffViolation> violations = new ArrayList<>();

    List<Schema> leftAllOf = null;
    List<Schema> leftOneOf = null;
    List<Schema> leftAnyOf = null;

    if (leftOasObject instanceof ComposedSchema) {
      leftAllOf = ((ComposedSchema) leftOasObject).getAllOf();
      leftOneOf = ((ComposedSchema) leftOasObject).getOneOf();
      leftAnyOf = ((ComposedSchema) leftOasObject).getAnyOf();
    }

    List<Schema> rightAllOf = null;
    List<Schema> rightOneOf = null;
    List<Schema> rightAnyOf = null;

    if (rightOasObject instanceof ComposedSchema) {
      rightAllOf = ((ComposedSchema) rightOasObject).getAllOf();
      rightOneOf = ((ComposedSchema) rightOasObject).getOneOf();
      rightAnyOf = ((ComposedSchema) rightOasObject).getAnyOf();
    }

    violations.addAll(
      doDiffValidateListProperty(
        context,
        "allOf",
        leftLocation,
        leftAllOf,
        rightLocation,
        rightAllOf,
        SCHEMA,
        schema -> System.identityHashCode(schema),
        singletonList(this)
      )
    );

    violations.addAll(
      doDiffValidateListProperty(
        context,
        "oneOf",
        leftLocation,
        leftOneOf,
        rightLocation,
        rightOneOf,
        SCHEMA,
        schema -> System.identityHashCode(schema),
        singletonList(this)
      )
    );

    violations.addAll(
      doDiffValidateListProperty(
        context,
        "anyOf",
        leftLocation,
        leftAnyOf,
        rightLocation,
        rightAnyOf,
        SCHEMA,
        schema -> System.identityHashCode(schema),
        singletonList(this)
      )
    );

    return violations;

  }
}
