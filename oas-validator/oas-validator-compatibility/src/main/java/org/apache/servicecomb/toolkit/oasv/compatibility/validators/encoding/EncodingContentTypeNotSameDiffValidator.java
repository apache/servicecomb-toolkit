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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.*;
import io.swagger.v3.oas.models.media.Encoding;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class EncodingContentTypeNotSameDiffValidator
  extends OasObjectDiffValidatorTemplate<Encoding>
  implements EncodingDiffValidator {

  @Override
  protected List<OasDiffViolation> validateCompare(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Encoding leftOasObject, OasObjectPropertyLocation rightLocation,
    Encoding rightOasObject) {

    if (Objects.equals(leftOasObject.getContentType(), rightOasObject.getContentType())) {
      return emptyList();
    }

    return singletonList(new OasDiffViolation(
      leftLocation.property("contentType"),
      rightLocation.property("contentType"),
      DiffViolationMessages.NEW_NOT_EQ_OLD
    ));

  }

}
