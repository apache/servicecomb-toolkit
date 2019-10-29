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

import org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils;
import org.apache.servicecomb.toolkit.oasv.validation.api.ViolationMessages;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.schema.SchemaPropertiesKeysValidator;

public class SchemaKeysValidators {

  public static final SchemaPropertiesKeysValidator PROPERTIES_LOWER_CAMEL_CASE_VALIDATOR = new SchemaPropertiesKeysValidator(
    key -> StringCaseUtils.isLowerCamelCase(key),
    key -> ViolationMessages.LOWER_CAMEL_CASE
  );

}
