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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.response;

import org.apache.servicecomb.toolkit.oasv.validation.api.MediaTypeValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ResponseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.MapPropertyValuesValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.MEDIA_TYPE;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#responseObject">Response Object</a>
 * .content property value validator
 */
public class ResponseContentValidator extends MapPropertyValuesValidator<ApiResponse, MediaType>
  implements ResponseValidator {

  public ResponseContentValidator(List<MediaTypeValidator> mediaTypeValidators) {
    super(mediaTypeValidators);
  }

  @Override
  protected String get$ref(ApiResponse oasObject) {
    return oasObject.get$ref();
  }

  @Override
  protected Map<String, MediaType> getMapProperty(ApiResponse oasObject) {
    return oasObject.getContent();
  }

  @Override
  protected String getMapPropertyName() {
    return "content";
  }

  @Override
  protected OasObjectType getValueType() {
    return MEDIA_TYPE;
  }

}
