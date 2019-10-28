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
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public abstract class SchemaPropertyChangeValidator<T> implements SchemaCompareValidator {

  @Override
  public final List<OasDiffViolation> validate(OasDiffValidationContext context, OasObjectPropertyLocation leftLocation,
    Schema leftOasObject, OasObjectPropertyLocation rightLocation, Schema rightOasObject) {

    if (!needValidate(context)) {
      return emptyList();
    }

    T leftNumber = getProperty(leftOasObject);
    T rightNumber = getProperty(rightOasObject);

    if (Objects.equals(leftNumber, rightNumber)) {
      return emptyList();
    }

    String propertyName = getPropertyName();

    if (leftNumber == null || rightNumber == null) {
      return singletonList(new OasDiffViolation(
          leftLocation.property(propertyName),
          rightLocation.property(propertyName),
          getMessage(leftNumber, rightNumber)
        )
      );
    }

    if (!isAllowed(leftNumber, rightNumber)) {
      return singletonList(new OasDiffViolation(
          leftLocation.property(propertyName),
          rightLocation.property(propertyName),
          getMessage(leftNumber, rightNumber)
        )
      );
    }

    return emptyList();

  }


  protected abstract T getProperty(Schema schema);

  protected abstract String getPropertyName();

  protected abstract String getMessage(T leftProperty, T rightProperty);

  protected abstract boolean isAllowed(T leftProperty, T rightProperty);

  protected abstract boolean needValidate(OasDiffValidationContext context);

}
