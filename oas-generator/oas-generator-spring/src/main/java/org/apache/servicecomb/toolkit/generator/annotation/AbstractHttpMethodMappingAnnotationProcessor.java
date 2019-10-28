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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.servicecomb.toolkit.generator.HttpStatus;
import org.apache.servicecomb.toolkit.generator.OperationContext;
import org.apache.servicecomb.toolkit.generator.util.ModelConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;

public abstract class AbstractHttpMethodMappingAnnotationProcessor<Annotation, Context> implements
    MethodAnnotationProcessor<Annotation, Context> {

  protected void processPath(String[] paths, OperationContext operationContext) {
    if (null == paths || paths.length == 0) {
      return;
    }

    if (paths.length > 1) {
      throw new Error(String.format("not allowed multi path for %s:%s",
          operationContext.getMethod().getDeclaringClass().getName(),
          operationContext.getMethod().getName()));
    }

    operationContext.setPath(paths[0]);
  }

  protected void processMethod(RequestMethod requestMethod, OperationContext operationContext) {
    operationContext.setHttpMethod(requestMethod.name());
  }

  protected void processConsumes(String[] consumes, OperationContext operationContext) {
    if (null == consumes || consumes.length == 0) {
      return;
    }
  }

  protected void processProduces(String[] produces, OperationContext operationContext) {
    if (null == produces || produces.length == 0) {
      return;
    }

    List<String> produceList = Arrays.stream(produces).filter(s -> !StringUtils.isEmpty(s))
        .collect(Collectors.toList());

    if (!produceList.isEmpty()) {
      ApiResponse apiResponse = new ApiResponse();
      Content content = new Content();
      MediaType mediaType = new MediaType();
      Schema schema = ModelConverter
          .getSchema(operationContext.getMethod().getReturnType(), operationContext.getComponents());
      mediaType.schema(schema);
      for (String produce : produceList) {
        content.addMediaType(produce, mediaType);
      }
      apiResponse.setContent(content);
      operationContext.addResponse(HttpStatus.OK, apiResponse);
    }
  }
}
