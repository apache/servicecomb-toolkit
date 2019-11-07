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

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.response.ResponseAddNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ResponseDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.HeaderDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.MediaTypeDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ResponseDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.response.ResponseContentDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.response.ResponseHeadersDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultResponseDiffValidatorFactory implements ResponseDiffValidatorFactory {

  private final MediaTypeDiffValidatorFactory mediaTypeDiffValidatorFactory;

  private final HeaderDiffValidatorFactory headerDiffValidatorFactory;

  public DefaultResponseDiffValidatorFactory(
      MediaTypeDiffValidatorFactory mediaTypeDiffValidatorFactory,
      HeaderDiffValidatorFactory headerDiffValidatorFactory) {
    this.mediaTypeDiffValidatorFactory = mediaTypeDiffValidatorFactory;
    this.headerDiffValidatorFactory = headerDiffValidatorFactory;
  }

  @Override
  public List<ResponseDiffValidator> create() {

    List<ResponseDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ResponseContentDiffValidator(mediaTypeDiffValidatorFactory.create()));
    validators.add(new ResponseHeadersDiffValidator(headerDiffValidatorFactory.create()));

    // concretes
    validators.add(new ResponseAddNotAllowedDiffValidator());

    return Collections.unmodifiableList(validators);
  }
}
