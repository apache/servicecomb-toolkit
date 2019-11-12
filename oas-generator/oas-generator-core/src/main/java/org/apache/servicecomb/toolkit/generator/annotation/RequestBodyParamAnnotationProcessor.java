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

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.generator.context.ParameterContext;
import org.apache.servicecomb.toolkit.generator.context.ParameterContext.InType;
import org.apache.servicecomb.toolkit.generator.util.SwaggerAnnotationUtils;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public class RequestBodyParamAnnotationProcessor implements ParamAnnotationProcessor<RequestBody, ParameterContext> {

  @Override
  public void process(RequestBody requestBodyAnnotation, ParameterContext context) {

    context.setIn(InType.BODY);
    io.swagger.v3.oas.models.parameters.RequestBody requestBody = new io.swagger.v3.oas.models.parameters.RequestBody();

    requestBody.setRequired(requestBodyAnnotation.required());
    requestBody.setDescription(requestBodyAnnotation.description());

    if (StringUtils.isNotEmpty(requestBodyAnnotation.ref())) {
      requestBody.set$ref(requestBodyAnnotation.ref());
    }

    Content[] contentAnnotations = requestBodyAnnotation.content();

    List<io.swagger.v3.oas.models.media.Content> contentsFromAnnotation = SwaggerAnnotationUtils
        .getContentFromAnnotation(contentAnnotations);

    Optional.ofNullable(contentsFromAnnotation).ifPresent(contents -> {
      requestBody.content(contents.get(0));
    });

    context.setRequestBody(requestBody);
  }
}
