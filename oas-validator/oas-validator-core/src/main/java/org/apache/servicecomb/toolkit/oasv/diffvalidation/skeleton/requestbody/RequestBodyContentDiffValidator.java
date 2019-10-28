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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.requestbody;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.*;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.MEDIA_TYPE;
import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasDiffValidationContextUtils.*;

public class RequestBodyContentDiffValidator
  extends MapPropertyDiffValidator<RequestBody, MediaType>
  implements RequestBodyDiffValidator {

  public RequestBodyContentDiffValidator(List<MediaTypeDiffValidator> mediaTypeValidators) {
    super(mediaTypeValidators);
  }

  @Override
  public List<OasDiffViolation> validate(OasDiffValidationContext context, OasObjectPropertyLocation leftLocation,
    RequestBody leftOasObject, OasObjectPropertyLocation rightLocation, RequestBody rightOasObject) {

    enterRequestBody(context);

    List<OasDiffViolation> violations = super
      .validate(context, leftLocation, leftOasObject, rightLocation, rightOasObject);

    leaveRequestBody(context);
    return violations;
  }

  @Override
  protected Map<String, MediaType> getMapProperty(RequestBody oasObject) {
    return oasObject.getContent();
  }

  @Override
  protected String getMapPropertyName() {
    return "content";
  }

  @Override
  protected OasObjectType getValueType() {
    return MEDIA_TYPE;
  }

}
