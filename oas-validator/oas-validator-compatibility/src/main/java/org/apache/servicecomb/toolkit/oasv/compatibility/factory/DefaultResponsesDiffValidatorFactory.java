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

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.ResponsesDiffValidator;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ResponseDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.factory.ResponsesDiffValidatorFactory;
import org.apache.servicecomb.toolkit.oasv.diffvalidation.skeleton.responses.ResponsesResponsesDiffValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultResponsesDiffValidatorFactory implements ResponsesDiffValidatorFactory {

  private final ResponseDiffValidatorFactory responseDiffValidatorFactory;

  public DefaultResponsesDiffValidatorFactory(
      ResponseDiffValidatorFactory responseDiffValidatorFactory) {
    this.responseDiffValidatorFactory = responseDiffValidatorFactory;
  }

  @Override
  public List<ResponsesDiffValidator> create() {

    List<ResponsesDiffValidator> validators = new ArrayList<>();

    // skeletons
    validators.add(new ResponsesResponsesDiffValidator(responseDiffValidatorFactory.create()));

    // concretes

    return Collections.unmodifiableList(validators);
  }
}
