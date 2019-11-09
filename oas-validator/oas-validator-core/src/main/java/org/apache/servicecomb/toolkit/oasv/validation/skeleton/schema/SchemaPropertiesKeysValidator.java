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
import org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#schemaObject">Schema Object</a>
 * .properties property key validator
 */
public class SchemaPropertiesKeysValidator
  extends SchemaRecursiveValidatorTemplate {

  private final Predicate<String> keyPredicate;

  private final Function<String, String> errorFunction;

  public SchemaPropertiesKeysValidator(Predicate<String> keyPredicate,
    Function<String, String> errorFunction) {
    this.keyPredicate = keyPredicate;
    this.errorFunction = errorFunction;
  }

  @Override
  protected List<OasViolation> validateCurrentSchemaObject(OasValidationContext context, Schema oasObject,
    OasObjectPropertyLocation location) {

    Map<String, Schema> properties = oasObject.getProperties();

    if (MapUtils.isEmpty(properties)) {
      return emptyList();
    }

    return
      OasObjectValidatorUtils.doValidateMapPropertyKeys(
        location,
        "properties",
        properties,
        keyPredicate,
        errorFunction
      );

  }
}
