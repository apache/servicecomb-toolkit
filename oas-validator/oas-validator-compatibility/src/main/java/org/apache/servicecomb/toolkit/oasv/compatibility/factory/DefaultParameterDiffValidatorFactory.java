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

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter.ParameterAddDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter.ParameterAllowEmptyValueChangeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter.ParameterAllowReservedChangeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter.ParameterExplodeNotSameDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter.ParameterRequiredChangeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.parameter.ParameterStyleNotSameDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ParameterDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.MediaTypeDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ParameterDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.SchemaDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.parameter.ParameterContentDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.parameter.ParameterSchemaDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultParameterDiffValidatorFactory implements ParameterDiffValidatorFactory {


  private final MediaTypeDiffValidatorFactory mediaTypeDiffValidatorFactory;

  private final SchemaDiffValidatorFactory schemaDiffValidatorFactory;

  public DefaultParameterDiffValidatorFactory(
      MediaTypeDiffValidatorFactory mediaTypeDiffValidatorFactory,
      SchemaDiffValidatorFactory schemaDiffValidatorFactory) {
    this.mediaTypeDiffValidatorFactory = mediaTypeDiffValidatorFactory;
    this.schemaDiffValidatorFactory = schemaDiffValidatorFactory;
  }

  @Override
  public List<ParameterDiffValidator> create() {

    List<ParameterDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ParameterContentDiffValidator(mediaTypeDiffValidatorFactory.create()));
    validators.add(new ParameterSchemaDiffValidator(schemaDiffValidatorFactory.create()));

    // concretes
    validators.add(new ParameterAddDiffValidator());
    validators.add(new ParameterRequiredChangeDiffValidator());
    validators.add(new ParameterAllowEmptyValueChangeDiffValidator());
    validators.add(new ParameterStyleNotSameDiffValidator());
    validators.add(new ParameterExplodeNotSameDiffValidator());
    validators.add(new ParameterAllowReservedChangeDiffValidator());

    return Collections.unmodifiableList(validators);
  }
}
