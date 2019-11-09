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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.mediatype;

import org.apache.servicecomb.toolkit.oasv.validation.api.EncodingValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.MediaTypeValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.MapPropertyValuesValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import io.swagger.v3.oas.models.media.Encoding;
import io.swagger.v3.oas.models.media.MediaType;

import java.util.List;
import java.util.Map;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.ENCODING;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#media-type-object">Media Type Object</a>
 * .encoding property value validator
 */
public class MediaTypeEncodingValidator extends MapPropertyValuesValidator<MediaType, Encoding>
  implements MediaTypeValidator {

  public MediaTypeEncodingValidator(List<EncodingValidator> encodingValidators) {
    super(encodingValidators);
  }

  @Override
  protected String get$ref(MediaType oasObject) {
    return null;
  }

  @Override
  protected Map<String, Encoding> getMapProperty(MediaType oasObject) {
    return oasObject.getEncoding();
  }

  @Override
  protected String getMapPropertyName() {
    return "encoding";
  }

  @Override
  protected OasObjectType getValueType() {
    return ENCODING;
  }

}
