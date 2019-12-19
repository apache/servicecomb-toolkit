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

import org.apache.servicecomb.toolkit.generator.context.OasContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestMappingClassAnnotationProcessor implements
    ClassAnnotationProcessor<RequestMapping, OasContext> {

  @Override
  public void process(RequestMapping requestMapping, OasContext oasContext) {

    String[] paths = requestMapping.value();
    if (null == paths || paths.length == 0) {
      return;
    }

    // swagger only support one basePath
    if (paths.length > 1) {
      throw new Error("not support multi path for " + oasContext.getCls().getName());
    }

    oasContext.setBasePath(paths[0]);

    processMethod(requestMapping.method(), oasContext);
    processConsumes(requestMapping.consumes(), oasContext);
    processProduces(requestMapping.produces(), oasContext);
    processHeaders(requestMapping.headers(), oasContext);
  }

  protected void processMethod(RequestMethod[] requestMethods, OasContext oasContext) {
    if (null == requestMethods || requestMethods.length == 0) {
      return;
    }

    if (requestMethods.length > 1) {
      throw new Error(
          "not allowed multi http method for " + oasContext.getCls().getName());
    }

    oasContext.setHttpMethod(requestMethods[0].name());
  }

  protected void processConsumes(String[] consumes, OasContext oasContext) {
    if (null == consumes || consumes.length == 0) {
      return;
    }
    oasContext.setConsumers(consumes);
  }

  protected void processProduces(String[] produces, OasContext oasContext) {
    if (null == produces || produces.length == 0) {
      return;
    }
    oasContext.setProduces(produces);
  }

  protected void processHeaders(String[] headers, OasContext oasContext) {
    if (null == headers || headers.length == 0) {
      return;
    }
    oasContext.setHeaders(headers);
  }
}

