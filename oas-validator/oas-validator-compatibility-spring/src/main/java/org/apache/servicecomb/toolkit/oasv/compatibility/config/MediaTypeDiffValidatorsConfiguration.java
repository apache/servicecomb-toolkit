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

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.MediaTypeDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeAddInParameterNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeDelInParameterNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeDelInRequestBodyNotAllowedDiffValidator;
import org.apache.servicecomb.toolkit.oasv.compatibility.validators.mediatype.MediaTypeDelInResponseNotAllowedDiffValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MediaTypeDiffValidatorsConfiguration {

  @Bean
  public MediaTypeDiffValidator mediaTypeAddInParameterNotAllowedDiffValidator() {
    return new MediaTypeAddInParameterNotAllowedDiffValidator();
  }

  @Bean
  public MediaTypeDiffValidator mediaTypeDelInParameterNotAllowedDiffValidator() {
    return new MediaTypeDelInParameterNotAllowedDiffValidator();
  }

  @Bean
  public MediaTypeDiffValidator mediaTypeDelInRequestBodyNotAllowedDiffValidator() {
    return new MediaTypeDelInRequestBodyNotAllowedDiffValidator();
  }

  @Bean
  public MediaTypeDiffValidator mediaTypeDelInResponseNotAllowedDiffValidator() {
    return new MediaTypeDelInResponseNotAllowedDiffValidator();
  }

}
