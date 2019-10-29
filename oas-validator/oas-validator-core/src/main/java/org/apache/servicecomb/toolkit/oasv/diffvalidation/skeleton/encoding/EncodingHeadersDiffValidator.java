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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.encoding;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.EncodingDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.HeaderDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.MapPropertyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Encoding;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.HEADER;

public class EncodingHeadersDiffValidator
  extends MapPropertyDiffValidator<Encoding, Header>
  implements EncodingDiffValidator {

  public EncodingHeadersDiffValidator(List<HeaderDiffValidator> diffValidators) {
    super(diffValidators);
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
