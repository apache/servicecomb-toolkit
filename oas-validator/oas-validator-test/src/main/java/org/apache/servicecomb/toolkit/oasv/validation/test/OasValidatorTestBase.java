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

package org.apache.servicecomb.toolkit.oasv.validation.test;

import io.swagger.v3.oas.models.OpenAPI;
import org.apache.servicecomb.toolkit.oasv.OasSpecLoader;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectPropertyLocation;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasValidationContext;
import org.apache.servicecomb.toolkit.oasv.validation.api.OasViolation;
import org.apache.servicecomb.toolkit.oasv.validation.factory.OasSpecValidatorFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class OasValidatorTestBase extends OasSpecLoader {

  @Autowired
  protected OasSpecValidatorFactory oasSpecValidatorFactory;

  final protected OasValidationContext createContext(OpenAPI openAPI) {
    OasValidationContext oasValidationContext = new OasValidationContext(openAPI);
    initContext(oasValidationContext);
    return oasValidationContext;
  }

  protected void initContext(OasValidationContext context) {
  }

  final protected OasViolation createViolation(String error, Object... path) {
    OasObjectPropertyLocation loc = OasObjectPropertyLocation.root();
    for (int i = 0; i < path.length; i = i + 2) {
      loc = loc.property((String) path[i], (OasObjectType) path[i + 1]);
    }
    return new OasViolation(loc, error);
  }

}
