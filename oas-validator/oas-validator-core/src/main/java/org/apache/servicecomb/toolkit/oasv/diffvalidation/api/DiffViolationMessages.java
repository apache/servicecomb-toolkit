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

public abstract class DiffViolationMessages {

  public static final String OP_DEL_FORBIDDEN = "不允许在新版本中删除";
  public static final String OP_ADD_FORBIDDEN = "不允许在新版本中添加";

  public static final String NEW_NOT_EQ_OLD = "新旧值不一致";
  public static final String NEW_NOT_GTE_OLD = "新值必须>=旧值";
  public static final String NEW_NOT_LTE_OLD = "新值必须<=旧值";

}
