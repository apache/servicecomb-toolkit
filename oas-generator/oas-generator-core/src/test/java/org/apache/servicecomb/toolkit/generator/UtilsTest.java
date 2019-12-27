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

import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.servicecomb.toolkit.generator.annotation.ModelInterceptor;
import org.apache.servicecomb.toolkit.generator.util.ModelConverter;
import org.apache.servicecomb.toolkit.generator.util.ParamUtils;
import org.apache.servicecomb.toolkit.generator.util.RequestResponse;
import org.apache.servicecomb.toolkit.generator.util.SwaggerAnnotationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

public class UtilsTest {

  @Test
  public void getParameterName() throws NoSuchMethodException {

    Method method = ParameterClass.class.getMethod("method", String.class);
    Parameter parameter = method.getParameters()[0];
    String parameterName = ParamUtils.getParameterName(method, parameter);
    Assert.assertEquals("param", parameterName);
  }

  @Test
  public void getSchema() {
    Schema schema = ModelConverter.getSchema(String.class);
    Assert.assertEquals(StringSchema.class, schema.getClass());

    schema = ModelConverter.getSchema(ParameterClass.class);
    Assert.assertEquals(Schema.class, schema.getClass());

    schema = ModelConverter.getSchema(String[].class);
    Assert.assertEquals(ArraySchema.class, schema.getClass());

    Components components = new Components();
    schema = ModelConverter.getSchema(ParameterClass[].class, components, null);
    Assert.assertEquals(ArraySchema.class, schema.getClass());

    schema = ModelConverter.getSchema(ParameterClass.class, components, null);
    Assert.assertNotNull(schema.get$ref());

    ModelInterceptor mockModelInterceptor = new ModelInterceptor() {
      @Override
      public int order() {
        return 0;
      }

      @Override
      public Schema process(Type cls, Components components) {
        return new Schema().name("unknown");
      }
    };

    ModelConverter.registerInterceptor(mockModelInterceptor);
    schema = ModelConverter.getSchema(ParameterClass.class);
    Assert.assertEquals("unknown", schema.getName());
    ModelConverter.unRegisterInterceptor(mockModelInterceptor);

    Components component = new Components();
    ModelConverter.getSchema(BeanClass.class, component, RequestResponse.REQUEST);
    Assert.assertNotNull(component.getSchemas().get("Value"));

    Schema beanClass = component.getSchemas().get("BeanClass");
    Assert.assertNotNull(beanClass);
    Schema valueRef = (Schema) beanClass.getProperties().get("value");
    Assert.assertEquals("#/components/schemas/Value", valueRef.get$ref());

    Schema intVal = (Schema) beanClass.getProperties().get("intVal");
    Assert.assertEquals("int32", intVal.getFormat());
    Assert.assertEquals("integer", intVal.getType());

    Schema intObj = (Schema) beanClass.getProperties().get("intObj");
    Assert.assertEquals("int32", intObj.getFormat());
    Assert.assertEquals("integer", intObj.getType());

    Schema longVal = (Schema) beanClass.getProperties().get("longVal");
    Assert.assertEquals("int64", longVal.getFormat());
    Assert.assertEquals("integer", longVal.getType());

    Schema longObj = (Schema) beanClass.getProperties().get("longObj");
    Assert.assertEquals("int64", longObj.getFormat());
    Assert.assertEquals("integer", longObj.getType());

    Schema doubleVal = (Schema) beanClass.getProperties().get("doubleVal");
    Assert.assertEquals("double", doubleVal.getFormat());
    Assert.assertEquals("number", doubleVal.getType());

    Schema doubleObj = (Schema) beanClass.getProperties().get("doubleObj");
    Assert.assertEquals("double", doubleObj.getFormat());
    Assert.assertEquals("number", doubleObj.getType());

    Schema props = (Schema) beanClass.getProperties().get("props");
    Assert.assertEquals(ArraySchema.class, props.getClass());
    Assert.assertEquals("array", props.getType());
    Assert.assertEquals("string", ((ArraySchema) props).getItems().getType());

    Schema numbers = (Schema) beanClass.getProperties().get("numbers");
    Assert.assertEquals(ArraySchema.class, numbers.getClass());
    Assert.assertEquals("array", numbers.getType());
    Assert.assertEquals("integer", ((ArraySchema) numbers).getItems().getType());
    Assert.assertEquals("int32", ((ArraySchema) numbers).getItems().getFormat());

    Schema values = (Schema) beanClass.getProperties().get("values");
    Assert.assertEquals(ArraySchema.class, values.getClass());
    Assert.assertEquals("array", values.getType());
    Assert.assertEquals("#/components/schemas/Value", ((ArraySchema) values).getItems().get$ref());
  }

  @Test
  public void getRequestOrResponseBean() {

    List<Type> requestBeanTypes = ModelConverter.getRequestBeanTypes(RequestBeanClass.class);
    List<Type> responseBeanTypes = ModelConverter.getResponseBeanTypes(ResponseBeanClass.class);

    Assert.assertNotNull(requestBeanTypes);
    Assert.assertNotNull(responseBeanTypes);

    Assert.assertEquals(1, requestBeanTypes.size());
    Assert.assertEquals("Value", ((Class) requestBeanTypes.get(0)).getSimpleName());
    Assert.assertEquals(1, responseBeanTypes.size());
    Assert.assertEquals("Value", ((Class) responseBeanTypes.get(0)).getSimpleName());
  }

  @Test
  public void getContentFromAnnotation() {
    Content contents = Mockito.mock(Content.class);
    when(contents.encoding()).thenReturn(new Encoding[] {Mockito.mock(Encoding.class)});
    List<io.swagger.v3.oas.models.media.Content> contentFromAnnotation = SwaggerAnnotationUtils
        .getContentFromAnnotation(contents);

    Assert.assertEquals(1, contentFromAnnotation.size());
  }

  class RequestBeanClass {

    public void setValue(Value value) {
    }

    public void setName(String name, String alias) {
    }

    public Integer getSomething() {
      return 0;
    }
  }

  class ResponseBeanClass {

    public Value getValue() {
      return new Value();
    }

    public String getName(String name) {
      return name;
    }

    public void setSomething(Integer something) {
    }
  }

  class ParameterClass {
    public void method(String param) {
    }
  }

  class BeanClass {

    private String name;

    private int intVal;

    private long longVal;

    private double doubleVal;

    private Integer intObj;

    private Long longObj;

    private Double doubleObj;

    private Value value;

    List<String> props;

    List<Integer> numbers;

    List<Value> values;

    List list;

    public List getList() {
      return list;
    }

    public void setList(List list) {
      this.list = list;
    }

    public int getIntVal() {
      return intVal;
    }

    public void setIntVal(int intVal) {
      this.intVal = intVal;
    }

    public long getLongVal() {
      return longVal;
    }

    public void setLongVal(long longVal) {
      this.longVal = longVal;
    }

    public double getDoubleVal() {
      return doubleVal;
    }

    public void setDoubleVal(double doubleVal) {
      this.doubleVal = doubleVal;
    }

    public Integer getIntObj() {
      return intObj;
    }

    public void setIntObj(Integer intObj) {
      this.intObj = intObj;
    }

    public Long getLongObj() {
      return longObj;
    }

    public void setLongObj(Long longObj) {
      this.longObj = longObj;
    }

    public Double getDoubleObj() {
      return doubleObj;
    }

    public void setDoubleObj(Double doubleObj) {
      this.doubleObj = doubleObj;
    }

    public List<Value> getValues() {
      return values;
    }

    public void setValues(List<Value> values) {
      this.values = values;
    }

    public List<Integer> getNumbers() {
      return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
      this.numbers = numbers;
    }

    public List<String> getProps() {
      return props;
    }

    public void setProps(List<String> props) {
      this.props = props;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Value getValue() {
      return value;
    }

    public void setValue(Value value) {
      this.value = value;
    }
  }

  class Value {
    private String prop1;

    private String prop2;

    public String getProp1() {
      return prop1;
    }

    public void setProp1(String prop1) {
      this.prop1 = prop1;
    }

    public String getProp2() {
      return prop2;
    }

    public void setProp2(String prop2) {
      this.prop2 = prop2;
    }
  }
}
