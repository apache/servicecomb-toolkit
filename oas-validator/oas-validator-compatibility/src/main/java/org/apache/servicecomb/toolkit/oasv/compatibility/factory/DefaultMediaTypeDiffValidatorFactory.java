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

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeAddInParameterNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeDelInParameterNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeDelInRequestBodyNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeDelInResponseNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.MediaTypeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.EncodingDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.MediaTypeDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.SchemaDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.mediatype.MediaTypeEncodingDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.mediatype.MediaTypeSchemaDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultMediaTypeDiffValidatorFactory implements MediaTypeDiffValidatorFactory {

  private final EncodingDiffValidatorFactory encodingDiffValidatorFactory;

  private final SchemaDiffValidatorFactory schemaDiffValidatorFactory;

  public DefaultMediaTypeDiffValidatorFactory(
      EncodingDiffValidatorFactory encodingDiffValidatorFactory,
      SchemaDiffValidatorFactory schemaDiffValidatorFactory) {
    this.encodingDiffValidatorFactory = encodingDiffValidatorFactory;
    this.schemaDiffValidatorFactory = schemaDiffValidatorFactory;
  }

  @Override
  public List<MediaTypeDiffValidator> create() {

    List<MediaTypeDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new MediaTypeEncodingDiffValidator(encodingDiffValidatorFactory.create()));
    validators.add(new MediaTypeSchemaDiffValidator(schemaDiffValidatorFactory.create()));

    // concretes
    validators.add(new MediaTypeAddInParameterNotAllowedDiffValidator());
    validators.add(new MediaTypeDelInParameterNotAllowedDiffValidator());
    validators.add(new MediaTypeDelInRequestBodyNotAllowedDiffValidator());
    validators.add(new MediaTypeDelInResponseNotAllowedDiffValidator());

    return Collections.unmodifiableList(validators);
  }
}
