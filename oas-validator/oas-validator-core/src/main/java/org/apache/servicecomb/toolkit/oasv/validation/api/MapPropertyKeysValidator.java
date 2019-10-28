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
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.servicecomb.toolkit.oasv.validation.util.OasObjectValidatorUtils.doValidateMapPropertyKeys;
import static java.util.Collections.emptyList;

public abstract class MapPropertyKeysValidator<T> implements OasObjectValidator<T> {

  private final Predicate<String> keyPredicate;

  private final Function<String, String> errorFunction;

  public MapPropertyKeysValidator(Predicate<String> keyPredicate,
    Function<String, String> errorFunction) {
    this.keyPredicate = keyPredicate;
    this.errorFunction = errorFunction;
  }

  @Override
  final public List<OasViolation> validate(OasValidationContext context, OasObjectPropertyLocation location,
    T oasObject) {

    if (StringUtils.isNotBlank(get$ref(oasObject))) {
      return emptyList();
    }

    return doValidateMapPropertyKeys(
      location,
      getMapPropertyName(),
      getMapProperty(oasObject),
      keyPredicate,
      errorFunction
    );

  }

  protected abstract String get$ref(T oasObject);

  protected abstract Map<String, ?> getMapProperty(T oasObject);

  protected abstract String getMapPropertyName();

}
