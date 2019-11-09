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

package org.apache.servicecomb.toolkit.oasv.validation.api;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * OpenAPI v3 validation violation
 */
public class OasViolation {

  /**
   * Object location
   */
  private final OasObjectPropertyLocation location;

  /**
   * Error message
   */
  private final String error;

  public OasViolation(OasObjectPropertyLocation location, String error) {
    this.location = location;
    this.error = error;
  }

  public OasObjectPropertyLocation getLocation() {
    return location;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", OasViolation.class.getSimpleName() + "[", "]")
      .add("location=" + location)
      .add("error='" + error + "'")
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OasViolation that = (OasViolation) o;
    return Objects.equals(location, that.location) &&
      Objects.equals(error, that.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, error);
  }
}
