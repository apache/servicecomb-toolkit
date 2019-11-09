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

public class ImportError {

  private final List<String> parseErrors = new ArrayList<>();

  private final List<OasViolation> violations = new ArrayList<>();

  private final List<OasDiffViolation> diffViolations = new ArrayList<>();

  @JsonIgnore
  public boolean isNotEmpty() {
    return CollectionUtils.isNotEmpty(parseErrors)
        || CollectionUtils.isNotEmpty(violations)
        || CollectionUtils.isNotEmpty(diffViolations);
  }

  /**
   * OAS Spec Yaml parse error
   *
   * @return
   */
  public List<String> getParseErrors() {
    return parseErrors;
  }

  /**
   * check style violations
   *
   * @return
   */
  public List<OasViolation> getViolations() {
    return violations;
  }

  /**
   * compatibility violations
   *
   * @return
   */
  public List<OasDiffViolation> getDiffViolations() {
    return diffViolations;
  }

  public void addParseErrors(List<String> syntaxErrors) {
    this.parseErrors.addAll(syntaxErrors);
  }

  public void addViolations(List<OasViolation> violations) {
    this.violations.addAll(violations);
  }

  public void addDiffViolations(List<OasDiffViolation> diffViolations) {
    this.diffViolations.addAll(diffViolations);
  }


}
