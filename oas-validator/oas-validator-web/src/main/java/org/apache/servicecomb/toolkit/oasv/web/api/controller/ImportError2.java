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

package org.apache.servicecomb.toolkit.oasv.web.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffViolation;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;

public class ImportError2 {

  private final List<String> leftParseErrors = new ArrayList<>();

  private final List<String> rightParseErrors = new ArrayList<>();

  private final List<OasDiffViolation> violations = new ArrayList<>();

  @JsonIgnore
  public boolean isNotEmpty() {
    return CollectionUtils.isNotEmpty(leftParseErrors)
        || CollectionUtils.isNotEmpty(rightParseErrors)
        || CollectionUtils.isNotEmpty(violations);
  }

  public List<String> getLeftParseErrors() {
    return leftParseErrors;
  }

  public List<String> getRightParseErrors() {
    return rightParseErrors;
  }


  /**
   * compatibility violations
   *
   * @return
   */
  public List<OasDiffViolation> getViolations() {
    return violations;
  }

  public void addLeftParseErrors(List<String> syntaxErrors) {
    this.leftParseErrors.addAll(syntaxErrors);
  }

  public void addRightParseErrors(List<String> syntaxErrors) {
    this.rightParseErrors.addAll(syntaxErrors);
  }


  public void addViolations(List<OasDiffViolation> diffViolations) {
    this.violations.addAll(diffViolations);
  }
}
