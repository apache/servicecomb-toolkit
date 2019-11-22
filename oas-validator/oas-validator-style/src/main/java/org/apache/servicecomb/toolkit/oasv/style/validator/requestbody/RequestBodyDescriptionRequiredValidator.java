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

package org.apache.servicecomb.toolkit.oasv.style.validator.requestbody;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.RequestBodyValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ObjectPropertyRequiredValidator;
import io.swagger.v3.oas.models.parameters.RequestBody;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#request-body-object">Request Object</a>
 * .description property validator
 * <ul>
 * <li>config item: requestBody.description.required=*boolean*</li>
 * <li>this field is required</li>
 * </ul>
 */
public class RequestBodyDescriptionRequiredValidator
  extends ObjectPropertyRequiredValidator<RequestBody, String>
  implements RequestBodyValidator {

  public static final String CONFIG_KEY = "requestBody.description.required";

  @Override
  protected String get$ref(RequestBody oasObject) {
    return oasObject.get$ref();
  }

  @Override
  protected String getPropertyObject(RequestBody oasObject) {
    return oasObject.getDescription();
  }

  @Override
  protected String getPropertyName() {
    return "description";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return null;
  }

}
