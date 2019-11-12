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

import java.util.Collections;

import org.apache.servicecomb.toolkit.generator.context.ParameterContext;
import org.apache.servicecomb.toolkit.generator.context.ParameterContext.InType;
import org.apache.servicecomb.toolkit.generator.util.SwaggerAnnotationUtils;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.media.Schema;

public class ParameterAnnotationProcessor implements ParamAnnotationProcessor<Parameter, ParameterContext> {

  @Override
  public void process(Parameter parameterAnnotation, ParameterContext paramCtx) {

    Schema schema = parameterAnnotation.schema();

    io.swagger.v3.oas.models.media.Schema schemaFromAnnotation = SwaggerAnnotationUtils.getSchemaFromAnnotation(schema);
    if (schemaFromAnnotation != null) {
      paramCtx.setSchema(schemaFromAnnotation);
    }

    paramCtx.setRequired(parameterAnnotation.required());
    paramCtx.setAllowEmptyValue(parameterAnnotation.allowEmptyValue());
    paramCtx.setAllowReserved(parameterAnnotation.allowReserved());
    paramCtx.setDeprecated(parameterAnnotation.deprecated());
    paramCtx.setExample(parameterAnnotation.example());

    switch (parameterAnnotation.in()) {
      case HEADER:
        paramCtx.setIn(InType.HEADER);
        break;
      case COOKIE:
        paramCtx.setIn(InType.COOKIE);
        break;
      case PATH:
        paramCtx.setIn(InType.PATH);
        break;
      case QUERY:
      case DEFAULT:
      default:
        paramCtx.setIn(InType.QUERY);
    }

    paramCtx.setDescription(parameterAnnotation.description());
    paramCtx.setRef(parameterAnnotation.ref());
    paramCtx.setName(parameterAnnotation.name());
    paramCtx.setExplode(getExplode(parameterAnnotation.explode()));
    paramCtx.applyAnnotations(Collections.singletonList(parameterAnnotation));
  }

  private Boolean getExplode(Explode explode) {

    switch (explode) {
      case TRUE: {
        return true;
      }
      case FALSE:
      case DEFAULT:
      default: {
        return false;
      }
    }
  }
}
