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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.parameter;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.SchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ObjectPropertyValidator;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SCHEMA;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#parameterObject">Parameter Object</a>属性校验器
 * .schema property validator
 */
public class ParameterSchemaValidator extends ObjectPropertyValidator<Parameter, Schema>
  implements ParameterValidator {

  public ParameterSchemaValidator(
    List<SchemaValidator> schemaValidators) {
    super(schemaValidators);
  }

  @Override
  protected String get$ref(Parameter oasObject) {
    return oasObject.get$ref();
  }

  @Override
  protected Schema getPropertyObject(Parameter oasObject) {
    return oasObject.getSchema();
  }

  @Override
  protected String getPropertyName() {
    return "schema";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return SCHEMA;
  }

}
