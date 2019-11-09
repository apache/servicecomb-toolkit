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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.api;

import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;

import java.util.Objects;
import java.util.StringJoiner;

public class OasDiffViolation {

  /**
   * left OAS object location
   */
  private final OasObjectPropertyLocation leftLocation;

  /**
   * right OAS object location
   */
  private final OasObjectPropertyLocation rightLocation;

  private final String error;

  public static OasDiffViolation onlyLeft(OasObjectPropertyLocation location, String error) {
    return new OasDiffViolation(location, null, error);
  }

  public static OasDiffViolation onlyRight(OasObjectPropertyLocation location, String error) {
    return new OasDiffViolation(null, location, error);
  }

  public OasDiffViolation(OasObjectPropertyLocation leftLocation,
    OasObjectPropertyLocation rightLocation, String error) {
    this.leftLocation = leftLocation;
    this.rightLocation = rightLocation;
    this.error = error;
  }

  public OasObjectPropertyLocation getLeftLocation() {
    return leftLocation;
  }

  public OasObjectPropertyLocation getRightLocation() {
    return rightLocation;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", OasDiffViolation.class.getSimpleName() + "[", "]")
      .add("leftLocation=" + leftLocation)
      .add("rightLocation=" + rightLocation)
      .add("error='" + error + "'")
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OasDiffViolation that = (OasDiffViolation) o;
    return Objects.equals(leftLocation, that.leftLocation) &&
      Objects.equals(rightLocation, that.rightLocation) &&
      Objects.equals(error, that.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leftLocation, rightLocation, error);
  }

}
