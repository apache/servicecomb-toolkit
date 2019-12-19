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

import org.apache.servicecomb.toolkit.generator.context.OperationContext;
import org.springframework.web.bind.annotation.RequestMethod;

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

    operationContext.setConsumers(consumes);
  }

  protected void processProduces(String[] produces, OperationContext operationContext) {
    if (null == produces || produces.length == 0) {
      return;
    }

    operationContext.setProduces(produces);
  }

  protected void processHeaders(String[] headers, OperationContext operationContext) {
    if (null == headers || headers.length == 0) {
      return;
    }
    operationContext.setHeaders(headers);
  }
}
