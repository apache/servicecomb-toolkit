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
import org.apache.servicecomb.toolkit.oasv.style.validator.paths.PathsKeyCaseValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.PathsValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.PathItemValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.factory.PathsValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.validation.skeleton.paths.PathsPathItemsValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DefaultPathsValidatorFactory implements PathsValidatorFactory {

  private final PathItemValidatorFactory pathItemValidatorFactory;

  public DefaultPathsValidatorFactory(
      PathItemValidatorFactory pathItemValidatorFactory) {
    this.pathItemValidatorFactory = pathItemValidatorFactory;
  }

  @Override
  public List<PathsValidator> create(FactoryOptions options) {
    List<PathsValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new PathsPathItemsValidator(pathItemValidatorFactory.create(options)));

    // concretes
    addPathsKeyCaseValidator(validators, options);
    return Collections.unmodifiableList(validators);
  }

  private void addPathsKeyCaseValidator(List<PathsValidator> validators, FactoryOptions options) {
    String expectedCase = options.getString(PathsKeyCaseValidator.CONFIG_KEY);
    if (expectedCase != null) {
      validators.add(new PathsKeyCaseValidator(expectedCase));
    }
  }

}
