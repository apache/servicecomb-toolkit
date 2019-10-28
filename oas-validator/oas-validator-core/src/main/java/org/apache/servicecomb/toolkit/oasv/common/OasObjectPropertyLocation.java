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

package org.apache.servicecomb.toolkit.oasv.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

import static java.util.Collections.singletonList;

/**
 * OpenAPI v3 对象所在的位置
 * <p>本对象immutable</p>
 */
public class OasObjectPropertyLocation {

  /**
   * 第一个元素是根对象，最后一个元素为当前对象，组合起来形成一个找到当前对象的路径
   */
  private final List<OasObjectProperty> path;

  private OasObjectPropertyLocation(OasObjectProperty path) {
    this(singletonList(path));
  }

  private OasObjectPropertyLocation(List<OasObjectProperty> path) {
    this.path = Collections.unmodifiableList(path);
  }

  /**
   * 创建一个根Location
   *
   * @return
   */
  public static OasObjectPropertyLocation root() {
    return new OasObjectPropertyLocation(new OasObjectProperty(OasObjectType.OPENAPI, "$"));
  }

  /**
   * 在当前路径下添加属性路径
   *
   * @param propertyName 属性名称
   * @param propertyType 属性的OAS对象类型
   * @return 获得新的 {@link OasObjectPropertyLocation}，原{@link OasObjectPropertyLocation}不变
   */
  public OasObjectPropertyLocation property(String propertyName, OasObjectType propertyType) {
    ArrayList<OasObjectProperty> oasObjectProperties = new ArrayList<>(path);
    oasObjectProperties.add(new OasObjectProperty(propertyType, propertyName));
    return new OasObjectPropertyLocation(oasObjectProperties);
  }

  /**
   * 在当前路径下添加非OAS Object类型的属性路径
   *
   * @param propertyName
   * @return 获得新的 {@link OasObjectPropertyLocation}，原{@link OasObjectPropertyLocation}不变
   */
  public OasObjectPropertyLocation property(String propertyName) {
    ArrayList<OasObjectProperty> oasObjectProperties = new ArrayList<>(path);
    oasObjectProperties.add(new OasObjectProperty(propertyName));
    return new OasObjectPropertyLocation(oasObjectProperties);
  }

  /**
   * 从根到属性的路径
   *
   * @return
   */
  public List<OasObjectProperty> getPath() {
    return path;
  }

  /**
   * 得到上级属性
   *
   * @return
   */
  @JsonIgnore
  public OasObjectProperty getParent() {
    if (path.size() <= 1) {
      return null;
    }
    return path.get(path.size() - 2);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OasObjectPropertyLocation that = (OasObjectPropertyLocation) o;
    return Objects.equals(path, that.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", OasObjectPropertyLocation.class.getSimpleName() + "[", "]")
      .add("path=" + path)
      .toString();
  }
}
