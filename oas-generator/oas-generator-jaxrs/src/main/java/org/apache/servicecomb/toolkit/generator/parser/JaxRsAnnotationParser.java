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

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.toolkit.generator.annotation.ConsumesAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.CookieParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.FormParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.HeaderParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.HttpMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.PathParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.QueryParamAnnotationProcessor;

import io.swagger.v3.oas.annotations.headers.Header;

public class JaxRsAnnotationParser extends AbstractAnnotationParser {

  @Override
  public void initClassAnnotationProcessor() {
    super.initClassAnnotationProcessor();
    classAnnotationMap.put(Path.class, new PathClassAnnotationProcessor());
  }

  @Override
  public void initMethodAnnotationProcessor() {
    super.initMethodAnnotationProcessor();
    methodAnnotationMap.put(Path.class, new PathMethodAnnotationProcessor());

    HttpMethodAnnotationProcessor httpMethodAnnotationProcessor = new HttpMethodAnnotationProcessor();
    methodAnnotationMap.put(GET.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(POST.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(DELETE.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(PATCH.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(PUT.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(OPTIONS.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(HEAD.class, httpMethodAnnotationProcessor);
    methodAnnotationMap.put(Consumes.class, new ConsumesAnnotationProcessor());
  }

  @Override
  public void initParameterAnnotationProcessor() {
    super.initParameterAnnotationProcessor();

    parameterAnnotationMap.put(QueryParam.class, new QueryParamAnnotationProcessor());
    parameterAnnotationMap.put(CookieParam.class, new CookieParamAnnotationProcessor());
    parameterAnnotationMap.put(FormParam.class, new FormParamAnnotationProcessor());
    parameterAnnotationMap.put(PathParam.class, new PathParamAnnotationProcessor());
    parameterAnnotationMap.put(Header.class, new HeaderParamAnnotationProcessor());
  }

  @Override
  public int getOrder() {
    return 100;
  }

  @Override
  public boolean canProcess(Class<?> cls) {

    if (cls.getAnnotation(Path.class) != null) {
      return true;
    }
    return false;
  }
}
