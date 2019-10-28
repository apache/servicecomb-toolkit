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

package org.apache.servicecomb.toolkit.oasv.compatibility.validators.schema;

import io.swagger.v3.oas.models.media.Schema;

import java.util.Objects;
import java.util.StringJoiner;

public class TypeFormat {
  private final String type;
  private final String format;

  public TypeFormat(String type, String format) {
    this.type = type;
    this.format = format;
  }

  public TypeFormat(Schema schema) {
    this.type = schema.getType();
    this.format = schema.getFormat();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TypeFormat that = (TypeFormat) o;
    return Objects.equals(type, that.type) &&
      Objects.equals(format, that.format);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, format);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TypeFormat.class.getSimpleName() + "[", "]")
      .add("type='" + type + "'")
      .add("format='" + format + "'")
      .toString();
  }

  public String getType() {
    return type;
  }

  public String getFormat() {
    return format;
  }
}
