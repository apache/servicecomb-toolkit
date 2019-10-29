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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.response;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.HeaderValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ResponseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.MapPropertyValuesValidator;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.HEADER;

public class ResponseHeadersValuesValidator extends MapPropertyValuesValidator<ApiResponse, Header>
  implements ResponseValidator {

  public ResponseHeadersValuesValidator(List<HeaderValidator> valueValidators) {
    super(valueValidators);
  }

  @Override
  protected String get$ref(ApiResponse oasObject) {
    return oasObject.get$ref();
  }

  @Override
  protected Map<String, Header> getMapProperty(ApiResponse components) {
    return components.getHeaders();
  }

  @Override
  protected String getMapPropertyName() {
    return "headers";
  }

  @Override
  protected OasObjectType getValueType() {
    return HEADER;
  }

}
