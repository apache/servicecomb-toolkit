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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaPropertyChangeValidator;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasDiffValidationContextUtils.isInParameter;
import static org.apache.servicecomb.toolkit.oasv.diffvalidation.util.OasDiffValidationContextUtils.isInRequestBody;
import static java.util.Collections.emptyList;

public class SchemaEnumChangeInRequestValidator extends SchemaPropertyChangeValidator<List> {

  public static final String VIOLATION_MESSAGE = "deleting enum on right side is not allowed";

  @Override
  protected List getProperty(Schema schema) {
    return ObjectUtils.defaultIfNull(schema.getEnum(), emptyList());
  }

  @Override
  protected String getPropertyName() {
    return "enum";
  }

  @Override
  protected boolean isAllowed(List leftProperty, List rightProperty) {
    return rightProperty.containsAll(leftProperty);
  }

  @Override
  protected String getMessage(List leftProperty, List rightProperty) {
    return VIOLATION_MESSAGE;
  }

  @Override
  protected boolean needValidate(OasDiffValidationContext context) {
    return isInParameter(context) || isInRequestBody(context);
  }

}
