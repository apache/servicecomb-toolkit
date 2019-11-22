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

package org.apache.servicecomb.toolkit.oasv.style.factory;

import org.apache.servicecomb.toolkit.oasv.FactoryOptions;
import org.apache.servicecomb.toolkit.oasv.style.validator.encoding.EncodingHeadersKeysCaseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.EncodingValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.EncodingValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.HeaderValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.encoding.EncodingHeadersValuesValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DefaultEncodingValidatorFactory implements EncodingValidatorFactory {

  private final HeaderValidatorFactory headerValidatorFactory;

  public DefaultEncodingValidatorFactory(
      HeaderValidatorFactory headerValidatorFactory) {
    this.headerValidatorFactory = headerValidatorFactory;
  }

  @Override
  public List<EncodingValidator> create(FactoryOptions options) {

    List<EncodingValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new EncodingHeadersValuesValidator(headerValidatorFactory.create(options)));

    // concrete
    addEncodingHeadersKeysCaseValidator(validators, options);
    return Collections.unmodifiableList(validators);
  }

  private void addEncodingHeadersKeysCaseValidator(List<EncodingValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(EncodingHeadersKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new EncodingHeadersKeysCaseValidator(expectedCase));
    }
  }

}
