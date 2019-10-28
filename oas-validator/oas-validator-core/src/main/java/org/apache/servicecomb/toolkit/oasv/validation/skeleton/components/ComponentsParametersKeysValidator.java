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
import org.apache.servicecomb.toolkit.oasv.validation.api.MapPropertyKeysValidator;
import io.swagger.v3.oas.models.Components;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComponentsParametersKeysValidator extends MapPropertyKeysValidator<Components>
  implements ComponentsValidator {

  public ComponentsParametersKeysValidator(Predicate<String> keyPredicate,
    Function<String, String> errorFunction) {
    super(keyPredicate, errorFunction);
  }
  @Override
  protected String get$ref(Components oasObject) {
    return null;
  }
  @Override
  protected Map<String, ?> getMapProperty(Components components) {
    return components.getParameters();
  }

  @Override
  protected String getMapPropertyName() {
    return "parameters";
  }

}
