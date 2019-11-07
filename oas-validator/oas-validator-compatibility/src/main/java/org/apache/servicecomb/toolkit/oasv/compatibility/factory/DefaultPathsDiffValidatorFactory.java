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

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.PathsDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.PathItemDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.PathsDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.paths.PathsPathItemsDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultPathsDiffValidatorFactory implements PathsDiffValidatorFactory {

  private final PathItemDiffValidatorFactory pathItemDiffValidatorFactory;

  public DefaultPathsDiffValidatorFactory(
      PathItemDiffValidatorFactory pathItemDiffValidatorFactory) {
    this.pathItemDiffValidatorFactory = pathItemDiffValidatorFactory;
  }

  @Override
  public List<PathsDiffValidator> create() {

    List<PathsDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new PathsPathItemsDiffValidator(pathItemDiffValidatorFactory.create()));

    // concretes
    return Collections.unmodifiableList(validators);
  }
}
