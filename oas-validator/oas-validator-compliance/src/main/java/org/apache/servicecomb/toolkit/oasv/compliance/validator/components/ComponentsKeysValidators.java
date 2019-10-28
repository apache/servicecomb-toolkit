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

import org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.*;

public abstract class ComponentsKeysValidators {

  public static final ComponentsCallbacksKeysValidator CALLBACKS_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsCallbacksKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );
  public static final ComponentsExamplesKeysValidator EXAMPLES_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsExamplesKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );
  public static final ComponentsHeadersKeysValidator HEADERS_UPPER_HYPHEN_CASE_VALIDATOR = new ComponentsHeadersKeysValidator(
    key -> StringCaseUtils.isUpperHyphenCase(key),
    key -> ViolationMessages.UPPER_HYPHEN_CASE
  );
  public static final ComponentsLinksKeysValidator LINKS_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsLinksKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );
  public static final ComponentsParametersKeysValidator PARAMETERS_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsParametersKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );
  public static final ComponentsRequestBodiesKeysValidator REQUEST_BODIES_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsRequestBodiesKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );
  public static final ComponentsResponsesKeysValidator RESPONSES_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsResponsesKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );
  public static final ComponentsSchemasKeysValidator SCHEMAS_UPPER_CAMEL_CASE_VALIDATOR = new ComponentsSchemasKeysValidator(
    key -> StringCaseUtils.isUpperCamelCase(key),
    key -> ViolationMessages.UPPER_CAMEL_CASE
  );

}
