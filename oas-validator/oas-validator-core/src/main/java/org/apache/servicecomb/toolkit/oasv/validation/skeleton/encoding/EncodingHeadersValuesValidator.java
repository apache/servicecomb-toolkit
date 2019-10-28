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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.encoding;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.EncodingValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.HeaderValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.MapPropertyValuesValidator;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Encoding;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.HEADER;

public class EncodingHeadersValuesValidator extends MapPropertyValuesValidator<Encoding, Header>
  implements EncodingValidator {

  public EncodingHeadersValuesValidator(List<HeaderValidator> valueValidators) {
    super(valueValidators);
  }

  @Override
  protected String get$ref(Encoding oasObject) {
    return null;
  }

  @Override
  protected Map<String, Header> getMapProperty(Encoding oasObject) {
    return oasObject.getHeaders();
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
