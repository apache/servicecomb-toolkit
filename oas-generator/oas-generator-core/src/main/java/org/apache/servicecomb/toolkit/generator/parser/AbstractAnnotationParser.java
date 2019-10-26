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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.servicecomb.toolkit.generator.OasContext;
import org.apache.servicecomb.toolkit.generator.OperationContext;
import org.apache.servicecomb.toolkit.generator.ParameterContext;
import org.apache.servicecomb.toolkit.generator.annotation.AnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ApiResponseMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ApiResponsesMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.MethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.OperationMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ParameterAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.parser.api.OpenApiAnnotationParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public abstract class AbstractAnnotationParser implements OpenApiAnnotationParser {

  private Class<?> cls;

  private OasContext context;

  protected Map<Class, ClassAnnotationProcessor> classAnnotationMap = new HashMap<>();

  protected Map<Class, MethodAnnotationProcessor> methodAnnotationMap = new HashMap<>();

  protected Map<Class, ParamAnnotationProcessor> parameterAnnotationMap = new HashMap<>();

  public AbstractAnnotationParser() {
    initMethodAnnotationProcessor();
    initClassAnnotationProcessor();
    initParameterAnnotationProcessor();
  }

  @Override
  public void parser(Class<?> cls, OasContext context) {

    this.cls = cls;
    this.context = context;

    if (!canProcess(cls)) {
      return;
    }

    for (Annotation clsAnnotation : cls.getAnnotations()) {
      AnnotationProcessor annotationProcessor = classAnnotationMap.get(clsAnnotation.annotationType());
      if (annotationProcessor == null) {
        continue;
      }
      annotationProcessor.process(clsAnnotation, context);
    }
    postParseClassAnnotaion(context);

    List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
    methods.sort(Comparator.comparing(Method::getName));
    for (Method m : methods) {
      OperationContext operationContext = new OperationContext(m, context);
      for (Annotation methodAnnotation : m.getAnnotations()) {
        MethodAnnotationProcessor annotationProcessor = methodAnnotationMap.get(methodAnnotation.annotationType());
        if (annotationProcessor != null) {
          annotationProcessor.process(methodAnnotation, operationContext);
        }
      }

      postParseMethodAnnotation(operationContext);

      java.lang.reflect.Parameter[] parameters = m.getParameters();

      for (java.lang.reflect.Parameter parameter : parameters) {
        ParameterContext parameterContext = new ParameterContext(operationContext, parameter);
        for (Annotation paramAnnotation : parameter.getAnnotations()) {
          ParamAnnotationProcessor paramAnnotationProcessor = parameterAnnotationMap
              .get(paramAnnotation.annotationType());
          if (paramAnnotationProcessor != null) {
            paramAnnotationProcessor.process(paramAnnotation, parameterContext);
          }
        }
        postParseParameterAnnotation(parameterContext);
      }
    }
  }

  @Override
  public void postParseClassAnnotaion(OasContext context) {
  }

  @Override
  public void postParseMethodAnnotation(OperationContext context) {
  }

  @Override
  public void postParseParameterAnnotation(ParameterContext context) {
  }

  public void initMethodAnnotationProcessor() {
    methodAnnotationMap.put(Operation.class, new OperationMethodAnnotationProcessor());
    methodAnnotationMap.put(ApiResponse.class, new ApiResponseMethodAnnotationProcessor());
    methodAnnotationMap.put(ApiResponses.class, new ApiResponsesMethodAnnotationProcessor());
  }

  public void initClassAnnotationProcessor() {

  }

  public void initParameterAnnotationProcessor() {
    parameterAnnotationMap.put(Parameter.class, new ParameterAnnotationProcessor());
  }

  @Override
  public ClassAnnotationProcessor findClassAnnotationProcessor(Class<? extends Annotation> annotationType) {
    return classAnnotationMap.get(annotationType);
  }

  @Override
  public MethodAnnotationProcessor findMethodAnnotationProcessor(Class<? extends Annotation> annotationType) {
    return methodAnnotationMap.get(annotationType);
  }

  @Override
  public ParamAnnotationProcessor findParameterAnnotationProcessor(Class<? extends Annotation> annotationType) {
    return parameterAnnotationMap.get(annotationType);
  }
}
