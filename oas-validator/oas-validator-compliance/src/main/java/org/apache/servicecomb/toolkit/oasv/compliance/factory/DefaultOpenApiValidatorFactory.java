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

package org.apache.servicecomb.toolkit.oasv.compliance.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi.OpenApiSecurityEmptyValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi.OpenApiTagNotEmptyValidator;
import org.apache.servicecomb.toolkit.oasv.compliance.validator.openapi.OpenApiVersionValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ComponentsValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.InfoValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OpenApiValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.PathsValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.ServerValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.TagValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.OpenApiComponentsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.OpenApiInfoValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.OpenApiPathsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.OpenApiServersValidator;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.OpenApiTagsValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultOpenApiValidatorFactory implements OpenApiValidatorFactory {

  private final TagValidatorFactory tagValidatorFactory;

  private final InfoValidatorFactory infoValidatorFactory;

  private final PathsValidatorFactory pathsValidatorFactory;

  private final ComponentsValidatorFactory componentsValidatorFactory;

  private final ServerValidatorFactory serverValidatorFactory;

  public DefaultOpenApiValidatorFactory(
      TagValidatorFactory tagValidatorFactory,
      InfoValidatorFactory infoValidatorFactory,
      PathsValidatorFactory pathsValidatorFactory,
      ComponentsValidatorFactory componentsValidatorFactory,
      ServerValidatorFactory serverValidatorFactory) {
    this.tagValidatorFactory = tagValidatorFactory;
    this.infoValidatorFactory = infoValidatorFactory;
    this.pathsValidatorFactory = pathsValidatorFactory;
    this.componentsValidatorFactory = componentsValidatorFactory;
    this.serverValidatorFactory = serverValidatorFactory;
  }

  @Override
  public List<OpenApiValidator> create() {

    List<OpenApiValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new OpenApiTagsValidator(tagValidatorFactory.create()));
    validators.add(new OpenApiInfoValidator(infoValidatorFactory.create()));
    validators.add(new OpenApiPathsValidator(pathsValidatorFactory.create()));
    validators.add(new OpenApiComponentsValidator(componentsValidatorFactory.create()));
    validators.add(new OpenApiServersValidator(serverValidatorFactory.create()));

    // concretes
    validators.add(new OpenApiSecurityEmptyValidator());
    validators.add(new OpenApiTagNotEmptyValidator());
    validators.add(new OpenApiVersionValidator());

    return Collections.unmodifiableList(validators);
  }
}
