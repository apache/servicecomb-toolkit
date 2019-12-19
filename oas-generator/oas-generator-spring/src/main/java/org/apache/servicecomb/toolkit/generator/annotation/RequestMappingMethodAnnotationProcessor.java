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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestMappingMethodAnnotationProcessor extends
    AbstractHttpMethodMappingAnnotationProcessor<RequestMapping, OperationContext> {

  @Override
  public void process(RequestMapping requestMapping, OperationContext operationContext) {

    this.processPath(requestMapping.path(), operationContext);
    this.processPath(requestMapping.value(), operationContext);
    this.processMethod(requestMapping.method(), operationContext);
    this.processConsumes(requestMapping.consumes(), operationContext);
    this.processProduces(requestMapping.produces(), operationContext);
    this.processHeaders(requestMapping.headers(), operationContext);
  }

  protected void processMethod(RequestMethod[] requestMethods, OperationContext operationContext) {
    if (null == requestMethods || requestMethods.length == 0) {
      return;
    }

    if (requestMethods.length > 1) {
      throw new Error(
          "not allowed multi http method for " + operationContext.getMethod().getName());
    }

    this.processMethod(requestMethods[0], operationContext);
  }
}
