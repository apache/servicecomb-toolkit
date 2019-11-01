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

import org.apache.servicecomb.toolkit.oasv.compliance.validator.info.InfoDescriptionRequiredValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.InfoValidator;
import org.apache.servicecomb.toolkit.oasv.validation.factory.InfoValidatorFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultInfoValidatorFactory implements InfoValidatorFactory {

  @Override
  public List<InfoValidator> create() {
    ArrayList<InfoValidator> validators = new ArrayList<>();

    // concretes
    validators.add(new InfoDescriptionRequiredValidator());

    return Collections.unmodifiableList(validators);
  }

}
