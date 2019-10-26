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

package org.apache.servicecomb.toolkit.generator.parser.api;

import java.lang.annotation.Annotation;

import org.apache.servicecomb.toolkit.generator.OasContext;
import org.apache.servicecomb.toolkit.generator.OperationContext;
import org.apache.servicecomb.toolkit.generator.ParameterContext;
import org.apache.servicecomb.toolkit.generator.annotation.ClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.MethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ParamAnnotationProcessor;

public interface OpenApiAnnotationParser {

  /**
   *
   * @param cls
   * @param context
   */
  void parser(Class<?> cls, OasContext context);

  /**
   * 用于排序, 对于同一个类，同时只能为springmvc或者jaxrs其中一种
   * @return
   */
  int getOrder();

  boolean canProcess(Class<?> cls);

  void postParseClassAnnotaion(OasContext context);

  void postParseMethodAnnotation(OperationContext context);

  void postParseParameterAnnotation(ParameterContext context);

  ClassAnnotationProcessor findClassAnnotationProcessor(Class<? extends Annotation> annotationType);

  MethodAnnotationProcessor findMethodAnnotationProcessor(Class<? extends Annotation> annotationType);

  ParamAnnotationProcessor findParameterAnnotationProcessor(Class<? extends Annotation> annotationType);
}
