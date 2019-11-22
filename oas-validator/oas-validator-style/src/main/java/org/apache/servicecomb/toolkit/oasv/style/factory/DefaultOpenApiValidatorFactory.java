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

package org.apache.servicecomb.toolkit.oasv.style.factory;

import org.apache.servicecomb.toolkit.oasv.FactoryOptions;
import org.apache.servicecomb.toolkit.oasv.style.validator.openapi.OpenApiSecuritySizeEqValidator;
import org.apache.servicecomb.toolkit.oasv.style.validator.openapi.OpenApiTagsSizeGteValidator;
import org.apache.servicecomb.toolkit.oasv.style.validator.openapi.OpenApiVersionGteValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.*;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.openapi.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
  public List<OpenApiValidator> create(FactoryOptions options) {

    List<OpenApiValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new OpenApiTagsValidator(tagValidatorFactory.create(options)));
    validators.add(new OpenApiInfoValidator(infoValidatorFactory.create(options)));
    validators.add(new OpenApiPathsValidator(pathsValidatorFactory.create(options)));
    validators.add(new OpenApiComponentsValidator(componentsValidatorFactory.create(options)));
    validators.add(new OpenApiServersValidator(serverValidatorFactory.create(options)));

    // concretes
    addOpenApiSecuritySizeValidator(validators, options);
    addOpenApiTagsSizeValidator(validators, options);
    addOpenApiVersionGteValidator(validators, options);

    return Collections.unmodifiableList(validators);
  }

  private void addOpenApiSecuritySizeValidator(List<OpenApiValidator> validators, FactoryOptions options) {
    Integer size = options.getInteger(OpenApiSecuritySizeEqValidator.CONFIG_KEY);
    if (size != null) {
      validators.add(new OpenApiSecuritySizeEqValidator(size));
    }
  }

  private void addOpenApiTagsSizeValidator(List<OpenApiValidator> validators, FactoryOptions options) {
    Integer size = options.getInteger(OpenApiTagsSizeGteValidator.CONFIG_KEY);
    if (size != null) {
      validators.add(new OpenApiTagsSizeGteValidator(size));
    }
  }

  private void addOpenApiVersionGteValidator(List<OpenApiValidator> validators, FactoryOptions options) {
    String versionGte = options.getString(OpenApiVersionGteValidator.CONFIG_KEY);
    if (versionGte != null) {
      validators.add(new OpenApiVersionGteValidator(versionGte));
    }
  }

}
