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

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OpenApiDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ComponentsDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.InfoDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.OpenApiDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.PathsDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ServerDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.TagDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiComponentsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiInfoDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiPathsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiServersDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.openapi.OpenApiTagsDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultOpenApiDiffValidatorFactory implements OpenApiDiffValidatorFactory {

  private final ComponentsDiffValidatorFactory componentsDiffValidatorFactory;

  private final InfoDiffValidatorFactory infoDiffValidatorFactory;

  private final PathsDiffValidatorFactory pathsDiffValidatorFactory;

  private final ServerDiffValidatorFactory serverDiffValidatorFactory;

  private final TagDiffValidatorFactory tagDiffValidatorFactory;

  public DefaultOpenApiDiffValidatorFactory(
      ComponentsDiffValidatorFactory componentsDiffValidatorFactory,
      InfoDiffValidatorFactory infoDiffValidatorFactory,
      PathsDiffValidatorFactory pathsDiffValidatorFactory,
      ServerDiffValidatorFactory serverDiffValidatorFactory,
      TagDiffValidatorFactory tagDiffValidatorFactory) {
    this.componentsDiffValidatorFactory = componentsDiffValidatorFactory;
    this.infoDiffValidatorFactory = infoDiffValidatorFactory;
    this.pathsDiffValidatorFactory = pathsDiffValidatorFactory;
    this.serverDiffValidatorFactory = serverDiffValidatorFactory;
    this.tagDiffValidatorFactory = tagDiffValidatorFactory;
  }

  @Override
  public List<OpenApiDiffValidator> create() {

    List<OpenApiDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new OpenApiComponentsDiffValidator(componentsDiffValidatorFactory.create()));
    validators.add(new OpenApiInfoDiffValidator(infoDiffValidatorFactory.create()));
    validators.add(new OpenApiPathsDiffValidator(pathsDiffValidatorFactory.create()));
    validators.add(new OpenApiServersDiffValidator(serverDiffValidatorFactory.create()));
    validators.add(new OpenApiTagsDiffValidator(tagDiffValidatorFactory.create()));

    // concretes

    return Collections.unmodifiableList(validators);
  }
}
