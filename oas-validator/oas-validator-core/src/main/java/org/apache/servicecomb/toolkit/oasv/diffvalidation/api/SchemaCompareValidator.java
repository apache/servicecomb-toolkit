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

import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.schema.SchemaDiffValidatorEngine;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;

public interface SchemaCompareValidator {

  /**
   * 左右两侧都有的情况，比较左右两侧对象。
   * <p>
   * 此方法不需要比较allOf、anyOf、oneOf、items、properties
   * </p>
   * 这些field的递归工作由{@link SchemaDiffValidatorEngine}完成。
   *
   * @param context
   * @param leftLocation
   * @param leftOasObject
   * @param rightLocation
   * @param rightOasObject
   * @return
   */
  List<OasDiffViolation> validate(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, Schema leftOasObject,
    OasObjectPropertyLocation rightLocation, Schema rightOasObject);

}
