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

package org.apache.servicecomb.toolkit.generator.parser;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.toolkit.generator.OperationContext;

import io.swagger.v3.oas.models.PathItem.HttpMethod;

public class ServicecombPojoParser extends AbstractAnnotationParser {

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public boolean canProcess(Class<?> cls) {
    if (cls.getAnnotation(RpcSchema.class) != null) {
      return true;
    }

    return false;
  }


  @Override
  public void postParseMethodAnnotation(OperationContext context) {
    super.postParseMethodAnnotation(context);

    Method currentMethod = context.getMethod();

    if (StringUtils.isEmpty(context.getHttpMethod())) {
      context.setHttpMethod(HttpMethod.POST.toString());
    }

    if (StringUtils.isEmpty(context.getOperationId())) {
      context.setOperationId(currentMethod.getName());
    }

    if (StringUtils.isEmpty(context.getPath())) {
      context.setPath(correctPath(currentMethod.getName()));
    }

    if (context.getApiResponses() == null || context.getApiResponses().size() == 0) {
      context.correctResponse(context.getApiResponses());
    }
  }

  private String correctPath(String path) {
    if (path == null || path.startsWith("/")) {
      return path;
    }
    return "/" + path;
  }
}
