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

package org.apache.servicecomb.toolkit.oasv.compliance.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.servicecomb.toolkit.oasv.compliance.validator.response.ResponseKeysValidators;
import org.apache.servicecomb.toolkit.oasv.validation.api.ResponseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.HeaderValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.MediaTypeValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ResponseValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.response.ResponseContentValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.response.ResponseHeadersValuesValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultResponseValidatorFactory implements ResponseValidatorFactory {

  private final MediaTypeValidatorFactory mediaTypeValidatorFactory;

  private final HeaderValidatorFactory headerValidatorFactory;

  public DefaultResponseValidatorFactory(
      MediaTypeValidatorFactory mediaTypeValidatorFactory,
      HeaderValidatorFactory headerValidatorFactory) {
    this.mediaTypeValidatorFactory = mediaTypeValidatorFactory;
    this.headerValidatorFactory = headerValidatorFactory;
  }

  @Override
  public List<ResponseValidator> create() {

    List<ResponseValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ResponseContentValidator(mediaTypeValidatorFactory.create()));
    validators.add(new ResponseHeadersValuesValidator(headerValidatorFactory.create()));

    // concretes
    validators.add(ResponseKeysValidators.HEADERS_UPPER_HYPHEN_CASE_VALIDATOR);

    return Collections.unmodifiableList(validators);
  }
}
