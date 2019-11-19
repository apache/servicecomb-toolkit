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

package org.apache.servicecomb.toolkit.oasv.compliance.validator.components;

import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsParametersKeysValidator;

import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isMatchCase;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#componentsObject">Components Object</a>
 * .parameters property key case validator
 * <ul>
 *   <li>config item: components.parameters.key.case=upper-camel-case/lower-camel-case/upper-hyphen-case</li>
 *   <li>must match case</li>
 * </ul>
 */
public class ComponentsParametersKeysCaseValidator extends ComponentsParametersKeysValidator {

  public static final String CONFIG_KEY = "components.parameters.key.case";
  public static final String ERROR = "Must be ";

  public ComponentsParametersKeysCaseValidator(String expectedCase) {
    super(
        key -> isMatchCase(expectedCase, key),
        key -> ERROR + expectedCase
    );
  }

}
