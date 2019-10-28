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

package org.apache.servicecomb.toolkit.oasv.validation.api;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils.doValidateProperty;
import static java.util.Collections.emptyList;

/**
 * OAS Object对象的对象类型的属性校验器
 *
 * @param <T> <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#table-of-contents">OpenAPI Specification</a>所定义的对象
 * @param <P> 属性的类型
 */
public abstract class ObjectPropertyValidator<T, P> implements OasObjectValidator<T> {

  private final List<? extends OasObjectValidator<P>> oasObjectValidators;

  protected ObjectPropertyValidator(List<? extends OasObjectValidator<P>> oasObjectValidators) {
    this.oasObjectValidators = oasObjectValidators;
  }

  @Override
  final public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    T oasObject) {
    if (StringUtils.isNotBlank(get$ref(oasObject))) {
      return emptyList();
    }

    P propertyObject = getPropertyObject(oasObject);
    if (propertyObject == null) {
      return emptyList();
    }
    OasObjectPropertyLocation propertyLoc = location.property(getPropertyName(), getPropertyType());
    return doValidateProperty(context, propertyLoc, propertyObject, oasObjectValidators);

  }

  protected abstract String get$ref(T oasObject);

  protected abstract P getPropertyObject(T oasObject);

  protected abstract String getPropertyName();

  protected abstract OasObjectType getPropertyType();

}
