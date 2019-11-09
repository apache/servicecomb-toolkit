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

import java.util.Objects;
import java.util.StringJoiner;

/**
 * OpenAPI V3 object property information
 */
public class OasObjectProperty {

  /**
   * property name
   */
  private final String name;

  /**
   * property object type, if null means primitive types, otherwise means OAS object
   */
  private final OasObjectType objectType;

  public OasObjectProperty(String name) {
    this(null, name);
  }

  public OasObjectProperty(OasObjectType objectType, String name) {
    this.objectType = objectType;
    this.name = name;
  }

  public OasObjectType getObjectType() {
    return objectType;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OasObjectProperty that = (OasObjectProperty) o;
    return objectType == that.objectType &&
      Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectType, name);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", OasObjectProperty.class.getSimpleName() + "[", "]")
      .add("name='" + name + "'")
      .add("objectType=" + objectType)
      .toString();
  }
}
