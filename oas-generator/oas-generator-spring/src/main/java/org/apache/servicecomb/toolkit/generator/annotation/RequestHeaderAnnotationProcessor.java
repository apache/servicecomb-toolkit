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

import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.toolkit.generator.ParameterContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ValueConstants;

import io.swagger.v3.oas.annotations.enums.ParameterIn;

public class RequestHeaderAnnotationProcessor implements ParamAnnotationProcessor<RequestHeader, ParameterContext> {

  @Override
  public void process(RequestHeader requestHeader, ParameterContext parameterContext) {
    parameterContext.setType(ParameterIn.HEADER.toString());
    parameterContext.setRequired(requestHeader.required());

    if (!ObjectUtils.isEmpty(requestHeader.defaultValue()) && !ValueConstants.DEFAULT_NONE
        .equals(requestHeader.defaultValue())) {
      parameterContext.setDefaultValue(requestHeader.defaultValue());
    }

    String name = requestHeader.name();
    if (StringUtils.isEmpty(name)) {
      name = requestHeader.value();
    }

    parameterContext.setName(name);
  }
}
