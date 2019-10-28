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

import org.apache.servicecomb.toolkit.oasv.compatibility.validators.encoding.*;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.EncodingDiffValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncodingDiffValidatorsConfiguration {

  @Bean
  public EncodingDiffValidator encodingAddNotAllowedDiffValidator() {
    return new EncodingAddNotAllowedDiffValidator();
  }

  @Bean
  public EncodingDiffValidator encodingDelNotAllowedDiffValidator() {
    return new EncodingDelNotAllowedDiffValidator();
  }

  @Bean
  public EncodingDiffValidator encodingAllowedReservedChangeDiffValidator() {
    return new EncodingAllowedReservedChangeDiffValidator();
  }

  @Bean
  public EncodingDiffValidator encodingContentTypeNotSameDiffValidator() {
    return new EncodingContentTypeNotSameDiffValidator();
  }

  @Bean
  public EncodingDiffValidator encodingExplodeNotSameDiffValidator() {
    return new EncodingExplodeNotSameDiffValidator();
  }

  @Bean
  public EncodingDiffValidator encodingStyleNotSameDiffValidator() {
    return new EncodingStyleNotSameDiffValidator();
  }

}
