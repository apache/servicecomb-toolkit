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

import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.jackson.AbstractModelConverter;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;

public class ArrayModelConverter extends AbstractModelConverter {

  protected ArrayModelConverter(ObjectMapper mapper) {
    super(mapper);
  }

  @Override
  public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {

    String typeName = _typeName(TypeFactory.defaultInstance().constructType(type.getType()));

    ArraySchema schema;
    if ("Array".equals(typeName)) {
      schema = new ArraySchema();
      if (!(type.getType() instanceof Class)) {
        return null;
      }
      Schema itemSchema = context.resolve(new AnnotatedType(((Class) type.getType()).getComponentType()));
      schema.setItems(itemSchema);
      return schema;
    }
    return super.resolve(type, context, chain);
  }
}
