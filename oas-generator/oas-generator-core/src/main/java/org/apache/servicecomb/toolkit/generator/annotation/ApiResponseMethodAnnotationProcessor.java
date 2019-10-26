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

package org.apache.servicecomb.toolkit.generator.annotation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.generator.OperationContext;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.media.MediaType;

public class ApiResponseMethodAnnotationProcessor implements
    MethodAnnotationProcessor<ApiResponse, OperationContext> {
  @Override
  public void process(ApiResponse response, OperationContext context) {

    io.swagger.v3.oas.models.responses.ApiResponse apiResponse = new io.swagger.v3.oas.models.responses.ApiResponse();

    Content[] contentAnnotations = response.content();
    Optional.ofNullable(contentAnnotations).ifPresent(contents -> {
      for (Content contentAnnotation : contents) {
        io.swagger.v3.oas.models.media.Content content = new io.swagger.v3.oas.models.media.Content();
        MediaType mediaType = new MediaType();
        content.addMediaType(contentAnnotation.mediaType(), mediaType);
        apiResponse.setContent(content);
      }
    });

    if (StringUtils.isNotEmpty(response.description())) {
      apiResponse.setDescription(response.description());
    }

    Header[] headersAnnotation = response.headers();
    Optional.ofNullable(headersAnnotation).ifPresent(headers -> {
      for (Header headerAnnotation : headers) {
        io.swagger.v3.oas.models.headers.Header header = new io.swagger.v3.oas.models.headers.Header();
        header.description(headerAnnotation.description());
        header.deprecated(headerAnnotation.deprecated());
        apiResponse.addHeaderObject(headerAnnotation.name(), header);
      }
    });

    context.addResponse(response.responseCode(), apiResponse);
  }
}
