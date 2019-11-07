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

package org.apache.servicecomb.toolkit.oasv.compatibility.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ComponentsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.CallbackDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ComponentsDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.HeaderDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.LinkDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ParameterDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.RequestBodyDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ResponseDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsCallbacksDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsHeadersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsLinksDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsParametersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsRequestBodiesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.components.ComponentsResponsesDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultComponentsDiffValidatorFactory implements ComponentsDiffValidatorFactory {

  private final CallbackDiffValidatorFactory callbackDiffValidatorFactory;

  private final HeaderDiffValidatorFactory headerDiffValidatorFactory;

  private final LinkDiffValidatorFactory linkDiffValidatorFactory;

  private final ParameterDiffValidatorFactory parameterDiffValidatorFactory;

  private final RequestBodyDiffValidatorFactory requestBodyDiffValidatorFactory;

  private final ResponseDiffValidatorFactory responseDiffValidatorFactory;

  public DefaultComponentsDiffValidatorFactory(
      CallbackDiffValidatorFactory callbackDiffValidatorFactory,
      HeaderDiffValidatorFactory headerDiffValidatorFactory,
      LinkDiffValidatorFactory linkDiffValidatorFactory,
      ParameterDiffValidatorFactory parameterDiffValidatorFactory,
      RequestBodyDiffValidatorFactory requestBodyDiffValidatorFactory,
      ResponseDiffValidatorFactory responseDiffValidatorFactory) {
    this.callbackDiffValidatorFactory = callbackDiffValidatorFactory;
    this.headerDiffValidatorFactory = headerDiffValidatorFactory;
    this.linkDiffValidatorFactory = linkDiffValidatorFactory;
    this.parameterDiffValidatorFactory = parameterDiffValidatorFactory;
    this.requestBodyDiffValidatorFactory = requestBodyDiffValidatorFactory;
    this.responseDiffValidatorFactory = responseDiffValidatorFactory;
  }

  @Override
  public List<ComponentsDiffValidator> create() {

    List<ComponentsDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ComponentsCallbacksDiffValidator(callbackDiffValidatorFactory.create()));
    validators.add(new ComponentsHeadersDiffValidator(headerDiffValidatorFactory.create()));
    validators.add(new ComponentsLinksDiffValidator(linkDiffValidatorFactory.create()));
    validators.add(new ComponentsParametersDiffValidator(parameterDiffValidatorFactory.create()));
    validators.add(new ComponentsRequestBodiesDiffValidator(requestBodyDiffValidatorFactory.create()));
    validators.add(new ComponentsResponsesDiffValidator(responseDiffValidatorFactory.create()));

    // concretes

    return Collections.unmodifiableList(validators);
  }
}
