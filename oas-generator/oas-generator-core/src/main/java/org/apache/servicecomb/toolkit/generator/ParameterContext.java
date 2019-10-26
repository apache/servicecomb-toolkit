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

package org.apache.servicecomb.toolkit.generator;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import org.apache.servicecomb.toolkit.generator.util.ModelConverter;
import org.apache.servicecomb.toolkit.generator.util.ParamUtils;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;

public class ParameterContext {

  private OperationContext parentContext;

  private boolean required;

  private String name;

  Parameter parameter;

  private Object defaultValue;


  io.swagger.v3.oas.models.parameters.Parameter oasParameter = new io.swagger.v3.oas.models.parameters.Parameter();

  public ParameterContext(OperationContext parentContext, Parameter parameter) {
    this.parentContext = parentContext;
    this.parameter = parameter;
    this.parentContext.addParameter(this);
  }


  public io.swagger.v3.oas.models.parameters.Parameter toOasParameter() {

    if (parameter == null) {
      return null;
    }
    ensureName();
    if (oasParameter.getSchema() == null) {
      Schema refSchema = ModelConverter.getSchema(parameter.getType(), getComponents());
      oasParameter.schema(refSchema);
    }

    if (oasParameter.getIn() == null) {
      oasParameter.setIn(ParameterIn.QUERY.toString());
    }

    if (defaultValue != null) {
      required = false;
      oasParameter.getSchema().setDefault(defaultValue);
    }

    oasParameter.setRequired(required);

    return oasParameter;
  }

  private void ensureName() {
    if (name == null) {
      // 尝试获取实际参数名
      name = ParamUtils.getParamterName(parentContext.getMethod(), parameter);
    }

    if (name == null) {
      name = parameter.getName();
    }

    oasParameter.setName(name);
  }

  public OperationContext getOperationContext() {
    return parentContext;
  }

  public Type getActualType() {
    return parameter.getParameterizedType();
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Components getComponents() {
    return parentContext.getComponents();
  }

  public io.swagger.v3.oas.models.parameters.Parameter getOasParameter() {
    return oasParameter;
  }

  public Parameter getParameter() {
    return parameter;
  }

  public void setParameter(Parameter parameter) {
    this.parameter = parameter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public void setType(String type) {
    oasParameter.setIn(type);
  }
}
