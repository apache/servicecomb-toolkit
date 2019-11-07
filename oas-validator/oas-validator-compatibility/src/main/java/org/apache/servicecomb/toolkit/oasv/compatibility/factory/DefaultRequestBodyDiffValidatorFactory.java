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

package org.apache.servicecomb.toolkit.oasv.compatibility.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.requestbody.RequestBodyRequiredChangeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.RequestBodyDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.MediaTypeDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.RequestBodyDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.requestbody.RequestBodyContentDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultRequestBodyDiffValidatorFactory implements RequestBodyDiffValidatorFactory {

  private final MediaTypeDiffValidatorFactory mediaTypeDiffValidatorFactory;

  public DefaultRequestBodyDiffValidatorFactory(
      MediaTypeDiffValidatorFactory mediaTypeDiffValidatorFactory) {
    this.mediaTypeDiffValidatorFactory = mediaTypeDiffValidatorFactory;
  }

  @Override
  public List<RequestBodyDiffValidator> create() {

    List<RequestBodyDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new RequestBodyContentDiffValidator(mediaTypeDiffValidatorFactory.create()));

    // concretes
    validators.add(new RequestBodyRequiredChangeDiffValidator());

    return Collections.unmodifiableList(validators);
  }
}
