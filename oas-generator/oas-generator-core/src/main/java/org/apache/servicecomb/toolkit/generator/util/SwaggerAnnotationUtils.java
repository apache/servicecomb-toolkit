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

package org.apache.servicecomb.toolkit.generator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;

public class SwaggerAnnotationUtils {

  public static List<Content> getContentFromAnnotation(
      io.swagger.v3.oas.annotations.media.Content... contentAnnotations) {

    if (contentAnnotations == null || contentAnnotations.length < 1) {
      return null;
    }

    List<Content> contents = new ArrayList<>();

    for (io.swagger.v3.oas.annotations.media.Content contentAnnotation : contentAnnotations) {
      io.swagger.v3.oas.models.media.Content content = new io.swagger.v3.oas.models.media.Content();
      MediaType mediaType = new MediaType();
      Encoding[] encodingAnnotations = contentAnnotation.encoding();
      Optional.ofNullable(encodingAnnotations).ifPresent(encodings -> {
        for (Encoding encodingAnnotation : encodings) {
          io.swagger.v3.oas.models.media.Encoding encoding = new io.swagger.v3.oas.models.media.Encoding();
          encoding.contentType(encodingAnnotation.contentType());
          encoding.allowReserved(encodingAnnotation.allowReserved());
          encoding.explode(encodingAnnotation.explode());
          mediaType.addEncoding(encodingAnnotation.name(), encoding);
        }
      });
      content.addMediaType(contentAnnotation.mediaType(), mediaType);
      contents.add(content);
    }
    return contents;
  }

  public static Schema getSchemaFromAnnotation(io.swagger.v3.oas.annotations.media.Schema schema) {
    if (schema == null) {
      return null;
    }
    Schema schemaObj = new Schema();
    schemaObj.setName(schema.name());
    schemaObj.setDescription(schema.description());
    schemaObj.setType(schema.type());
    schemaObj.setTitle(schema.title());
    schemaObj.setNullable(schema.nullable());
    schemaObj.setDefault(schema.defaultValue());
    schemaObj.setFormat(schema.format());
    schemaObj.setDeprecated(schema.deprecated());
    Map<String, Object> extensionsFromAnnotation = getExtensionsFromAnnotation(schema.extensions());
    schemaObj.extensions(extensionsFromAnnotation);
    return schemaObj;
  }

  public static Map<String, Object> getExtensionsFromAnnotation(Extension... extensions) {
    if (extensions == null || extensions.length < 1) {
      return null;
    }

    Map<String, Object> extensionMap = new HashMap<>();
    for (Extension extension : extensions) {
      ExtensionProperty[] properties = extension.properties();
      Optional.ofNullable(properties).ifPresent(props -> {
        for (ExtensionProperty prop : props) {
          extensionMap.put(prop.name(), prop.value());
        }
      });
    }

    return extensionMap;
  }
}
