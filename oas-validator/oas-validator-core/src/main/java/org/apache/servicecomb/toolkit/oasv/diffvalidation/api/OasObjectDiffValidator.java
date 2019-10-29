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
 * OAS Object差异校验器接口
 *
 * @param <T> <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#table-of-contents">OpenAPI Specification</a>所定义的对象
 */
public interface OasObjectDiffValidator<T> {

  /**
   * leftOasObject 和 rightOasObject 不可同时为null
   *
   * @param context        差异校验上下文
   * @param leftLocation   左侧OAS Object位置（可能为空）
   * @param leftOasObject  左侧OAS Object（可能为空）
   * @param rightLocation  右侧OAS Object位置（可能为空）
   * @param rightOasObject 右侧OAS Object（可能为空）
   * @return 差异校验违例
   */
  List<OasDiffViolation> validate(OasDiffValidationContext context,
    OasObjectPropertyLocation leftLocation, T leftOasObject,
    OasObjectPropertyLocation rightLocation, T rightOasObject);

}
