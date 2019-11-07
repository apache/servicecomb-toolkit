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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaCompareValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.util.ChangeRangeCheckUtils;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public abstract class SchemaTypeFormatChangeValidator implements SchemaCompareValidator {

  @Override
  public final List<OasDiffViolation> validate(OasDiffValidationContext context, OasObjectPropertyLocation leftLocation,
    Schema leftOasObject, OasObjectPropertyLocation rightLocation, Schema rightOasObject) {

    if (!needValidate(context)) {
      return emptyList();
    }

    TypeFormat leftTypeFormat = new TypeFormat(leftOasObject);
    TypeFormat rightTypeFormat = new TypeFormat(rightOasObject);

    if (ChangeRangeCheckUtils.isNotViolated(leftTypeFormat, rightTypeFormat, getAllowedChangedList())) {
      return emptyList();
    }

    StringBuilder message = new StringBuilder("the change ");
    message
      .append("(type=")
      .append(leftTypeFormat.getType())
      .append(",format=")
      .append(leftTypeFormat.getFormat())
      .append(')')
      .append("->")
      .append("(type=")
      .append(rightTypeFormat.getType())
      .append(",format=")
      .append(rightTypeFormat.getFormat())
      .append(')')
      .append(" is not allowed");

    return singletonList(new OasDiffViolation(leftLocation, rightLocation, message.toString()));

  }

  protected abstract List<Object[]> getAllowedChangedList();

  protected abstract boolean needValidate(OasDiffValidationContext context);

}
