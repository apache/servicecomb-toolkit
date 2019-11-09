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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.pathitem;

import org.apache.servicecomb.toolkit.oasv.validation.api.ParameterValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ListPropertyValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathItemValidator;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.PARAMETER;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#pathItemObject">Path Item Object</a>
 * .parameters (List of <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#parameterObject">Parameter Object</a>)
 * validator
 */
public class PathItemParametersValidator extends ListPropertyValidator<PathItem, Parameter>
  implements PathItemValidator {

  public PathItemParametersValidator(List<ParameterValidator> parameterValidators) {
    super(parameterValidators);
  }

  @Override
  protected String get$ref(PathItem oasObject) {
    return oasObject.get$ref();
  }

  @Override
  protected List<Parameter> getListProperty(PathItem oasObject) {
    return oasObject.getParameters();
  }

  @Override
  protected String getListPropertyName() {
    return "parameters";
  }

  @Override
  protected OasObjectType getElementType() {
    return PARAMETER;
  }

}
