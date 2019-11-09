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

import java.util.List;

/**
 * OAS Object difference validator interface
 *
 * @param <T> <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#table-of-contents">OpenAPI Specification</a> Object
 */
public interface OasObjectDiffValidator<T> {

  /**
   * leftOasObject and rightOasObject should not be null at the same time
   *
   * @param context        difference validation context
   * @param leftLocation   left OAS Object location (maybe null)
   * @param leftOasObject  left OAS Object (maybe null)
   * @param rightLocation  right OAS Object location (maybe null)
   * @param rightOasObject right OAS Object (maybe null)
   * @return difference validation violations, maybe a empty list
   */
  List<OasDiffViolation> validate(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject);

}
