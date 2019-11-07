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

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.EncodingAddNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.EncodingAllowedReservedChangeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.EncodingContentTypeNotSameDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.EncodingDelNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.EncodingExplodeNotSameDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.EncodingStyleNotSameDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.EncodingDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.EncodingDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.HeaderDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.encoding.EncodingHeadersDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultEncodingDiffValidatorFactory implements EncodingDiffValidatorFactory {

  private final HeaderDiffValidatorFactory headerDiffValidatorFactory;

  public DefaultEncodingDiffValidatorFactory(
      HeaderDiffValidatorFactory headerDiffValidatorFactory) {
    this.headerDiffValidatorFactory = headerDiffValidatorFactory;
  }

  @Override
  public List<EncodingDiffValidator> create() {

    List<EncodingDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new EncodingHeadersDiffValidator(headerDiffValidatorFactory.create()));

    // concretes
    validators.add(new EncodingAddNotAllowedDiffValidator());
    validators.add(new EncodingDelNotAllowedDiffValidator());
    validators.add(new EncodingAllowedReservedChangeDiffValidator());
    validators.add(new EncodingContentTypeNotSameDiffValidator());
    validators.add(new EncodingExplodeNotSameDiffValidator());
    validators.add(new EncodingStyleNotSameDiffValidator());

    return Collections.unmodifiableList(validators);
  }
}
