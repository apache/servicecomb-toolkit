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

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaDiscriminatorChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaReadOnlyChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaWriteOnlyChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaXmlChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaEnumChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaExclusiveMaximumChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaExclusiveMinimumChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMaxItemsChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMaxLengthChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMaxPropertiesChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMaximumChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMinItemsChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMinLengthChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMinPropertiesChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMinimumChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaMultipleOfChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaNullableChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaRequiredChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaTypeFormatChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.SchemaUniqueItemsChangeInRequestValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaEnumChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaExclusiveMaximumChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaExclusiveMinimumChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMaxItemsChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMaxLengthChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMaxPropertiesChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMaximumChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMinItemsChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMinLengthChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMinPropertiesChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMinimumChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaMultipleOfChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaNullableChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaRequiredChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaTypeFormatChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.SchemaUniqueItemsChangeInResponseValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaCompareValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.SchemaDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.schema.SchemaDiffValidatorEngine;
import org.springframework.stereotype.Component;

@Component
public class DefaultSchemaDiffValidatorFactory implements SchemaDiffValidatorFactory {


  @Override
  public List<SchemaDiffValidator> create() {

    List<SchemaDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new SchemaDiffValidatorEngine(
        Collections.emptyList(),
        Collections.emptyList(),
        compareValidators()
    ));

    // concretes

    return Collections.unmodifiableList(validators);
  }

  private List<SchemaCompareValidator> compareValidators() {

    List<SchemaCompareValidator> compareValidators = new ArrayList<>();

    // in common conntext
    compareValidators.add(new SchemaReadOnlyChangeValidator());
    compareValidators.add(new SchemaWriteOnlyChangeValidator());
    compareValidators.add(new SchemaXmlChangeValidator());
    compareValidators.add(new SchemaDiscriminatorChangeValidator());

    // for request context
    compareValidators.add(new SchemaTypeFormatChangeInRequestValidator());
    compareValidators.add(new SchemaMaximumChangeInRequestValidator());
    compareValidators.add(new SchemaExclusiveMinimumChangeInRequestValidator());
    compareValidators.add(new SchemaExclusiveMaximumChangeInRequestValidator());
    compareValidators.add(new SchemaMultipleOfChangeInRequestValidator());
    compareValidators.add(new SchemaMaxItemsChangeInRequestValidator());
    compareValidators.add(new SchemaMaxLengthChangeInRequestValidator());
    compareValidators.add(new SchemaMaxPropertiesChangeInRequestValidator());
    compareValidators.add(new SchemaMinimumChangeInRequestValidator());
    compareValidators.add(new SchemaMinItemsChangeInRequestValidator());
    compareValidators.add(new SchemaMinLengthChangeInRequestValidator());
    compareValidators.add(new SchemaMinPropertiesChangeInRequestValidator());
    compareValidators.add(new SchemaUniqueItemsChangeInRequestValidator());
    compareValidators.add(new SchemaRequiredChangeInRequestValidator());
    compareValidators.add(new SchemaEnumChangeInRequestValidator());
    compareValidators.add(new SchemaNullableChangeInRequestValidator());

    // for response context
    compareValidators.add(new SchemaTypeFormatChangeInResponseValidator());
    compareValidators.add(new SchemaMaximumChangeInResponseValidator());
    compareValidators.add(new SchemaExclusiveMinimumChangeInResponseValidator());
    compareValidators.add(new SchemaExclusiveMaximumChangeInResponseValidator());
    compareValidators.add(new SchemaMultipleOfChangeInResponseValidator());
    compareValidators.add(new SchemaMaxItemsChangeInResponseValidator());
    compareValidators.add(new SchemaMaxLengthChangeInResponseValidator());
    compareValidators.add(new SchemaMaxPropertiesChangeInResponseValidator());
    compareValidators.add(new SchemaMinimumChangeInResponseValidator());
    compareValidators.add(new SchemaMinItemsChangeInResponseValidator());
    compareValidators.add(new SchemaMinLengthChangeInResponseValidator());
    compareValidators.add(new SchemaMinPropertiesChangeInResponseValidator());
    compareValidators.add(new SchemaUniqueItemsChangeInResponseValidator());
    compareValidators.add(new SchemaRequiredChangeInResponseValidator());
    compareValidators.add(new SchemaEnumChangeInResponseValidator());
    compareValidators.add(new SchemaNullableChangeInResponseValidator());

    return Collections.unmodifiableList(compareValidators);
  }
}
