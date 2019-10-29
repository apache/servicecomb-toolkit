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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi;

import org.apache.servicecomb.toolkit.oasv.validation.api.InfoValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.ObjectPropertyValidator;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.List;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#openapi-object">OpenAPI Object</a>
 * .info (<a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#infoObject">Info Object</a>)校验器
 */
public class OpenApiInfoValidator extends ObjectPropertyValidator<OpenAPI, Info>
  implements OpenApiValidator {

  public OpenApiInfoValidator(List<InfoValidator> infoValidators) {
    super(infoValidators);
  }

  @Override
  protected String get$ref(OpenAPI oasObject) {
    return null;
  }

  @Override
  protected Info getPropertyObject(OpenAPI oasObject) {
    return oasObject.getInfo();
  }

  @Override
  protected String getPropertyName() {
    return "info";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return OasObjectType.INFO;
  }

}
