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

package org.apache.servicecomb.toolkit.oasv.compatibility.config;

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.request.*;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.response.*;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.SchemaCompareValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaDiscriminatorChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaReadOnlyChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaWriteOnlyChangeValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema.SchemaXmlChangeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaDiffValidatorsConfiguration {

  @Bean
  public SchemaCompareValidator schemaTypeFormatInRequestCompareValidator() {
    return new SchemaTypeFormatChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaTypeFormatInResponseCompareValidator() {
    return new SchemaTypeFormatChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMultipleOfChangeInRequestValidator() {
    return new SchemaMultipleOfChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaximumChangeInResponseValidator() {
    return new SchemaMaximumChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaximumChangeInRequestValidator() {
    return new SchemaMaximumChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaExclusiveMinimumChangeInRequestValidator() {
    return new SchemaExclusiveMinimumChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaExclusiveMaximumChangeInRequestValidator() {
    return new SchemaExclusiveMaximumChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaExclusiveMaximumChangeInResponseValidator() {
    return new SchemaExclusiveMaximumChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaExclusiveMinimumChangeInResponseValidator() {
    return new SchemaExclusiveMinimumChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaxItemsChangeInRequestValidator() {
    return new SchemaMaxItemsChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaxLengthChangeInRequestValidator() {
    return new SchemaMaxLengthChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaxPropertiesChangeInRequestValidator() {
    return new SchemaMaxPropertiesChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinimumChangeInRequestValidator() {
    return new SchemaMinimumChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinItemsChangeInRequestValidator() {
    return new SchemaMinItemsChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinLengthChangeInRequestValidator() {
    return new SchemaMinLengthChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinPropertiesChangeInRequestValidator() {
    return new SchemaMinPropertiesChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaUniqueItemsChangeInRequestValidator() {
    return new SchemaUniqueItemsChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaRequiredChangeInRequestValidator() {
    return new SchemaRequiredChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaEnumChangeInRequestValidator() {
    return new SchemaEnumChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaNullableChangeInRequestValidator() {
    return new SchemaNullableChangeInRequestValidator();
  }

  @Bean
  public SchemaCompareValidator schemaReadOnlyChangeValidator() {
    return new SchemaReadOnlyChangeValidator();
  }

  @Bean
  public SchemaCompareValidator schemaWriteOnlyChangeValidator() {
    return new SchemaWriteOnlyChangeValidator();
  }

  @Bean
  public SchemaCompareValidator schemaXmlChangeValidator() {
    return new SchemaXmlChangeValidator();
  }

  @Bean
  public SchemaCompareValidator schemaDiscriminatorChangeValidator() {
    return new SchemaDiscriminatorChangeValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMultipleOfChangeInResponseValidator() {
    return new SchemaMultipleOfChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaxItemsChangeInResponseValidator() {
    return new SchemaMaxItemsChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaxLengthChangeInResponseValidator() {
    return new SchemaMaxLengthChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMaxPropertiesChangeInResponseValidator() {
    return new SchemaMaxPropertiesChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinimumChangeInResponseValidator() {
    return new SchemaMinimumChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinItemsChangeInResponseValidator() {
    return new SchemaMinItemsChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinLengthChangeInResponseValidator() {
    return new SchemaMinLengthChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaMinPropertiesChangeInResponseValidator() {
    return new SchemaMinPropertiesChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaUniqueItemsChangeInResponseValidator() {
    return new SchemaUniqueItemsChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaRequiredChangeInResponseValidator() {
    return new SchemaRequiredChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaEnumChangeInResponseValidator() {
    return new SchemaEnumChangeInResponseValidator();
  }

  @Bean
  public SchemaCompareValidator schemaNullableChangeInResponseValidator() {
    return new SchemaNullableChangeInResponseValidator();
  }
}
