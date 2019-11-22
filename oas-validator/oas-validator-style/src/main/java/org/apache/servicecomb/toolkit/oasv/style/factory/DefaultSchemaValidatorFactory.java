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
import org.apache.servicecomb.toolkit.oasv.style.validator.schema.SchemaPropertiesKeysCaseValidator;
import org.apache.servicecomb.toolkit.oasv.style.validator.schema.SchemaTitleRequiredValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.SchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.SchemaValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DefaultSchemaValidatorFactory implements SchemaValidatorFactory {

  @Override
  public List<SchemaValidator> create(FactoryOptions options) {
    ArrayList<SchemaValidator> validators = new ArrayList<>();

    // concretes
    addSchemaTitleRequiredValidator(validators, options);
    addSchemaPropertiesKeysCaseValidator(validators, options);
    return Collections.unmodifiableList(validators);
  }

  private void addSchemaTitleRequiredValidator(List<SchemaValidator> validators, FactoryOptions options) {
    Boolean required = options.getBoolean(SchemaTitleRequiredValidator.CONFIG_KEY);
    if (Boolean.TRUE.equals(required)) {
      validators.add(new SchemaTitleRequiredValidator());
    }
  }

  private void addSchemaPropertiesKeysCaseValidator(List<SchemaValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(SchemaPropertiesKeysCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new SchemaPropertiesKeysCaseValidator(expectedCase));
    }
  }

}
