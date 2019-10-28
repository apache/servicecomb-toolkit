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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.components;

import org.apache.servicecomb.toolkit.oasv.validation.api.ComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.MapPropertyValuesValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.ExampleValidator;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.EXAMPLE;

public class ComponentsExamplesValuesValidator extends MapPropertyValuesValidator<Components, Example>
  implements ComponentsValidator {

  public ComponentsExamplesValuesValidator(List<ExampleValidator> valueValidators) {
    super(valueValidators);
  }
  @Override
  protected String get$ref(Components oasObject) {
    return null;
  }
  @Override
  protected Map<String, Example> getMapProperty(Components components) {
    return components.getExamples();
  }

  @Override
  protected String getMapPropertyName() {
    return "examples";
  }

  @Override
  protected OasObjectType getValueType() {
    return EXAMPLE;
  }

}
