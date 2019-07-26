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

package org.apache.servicecomb.toolkit.codegen;

import java.util.Map;

import org.apache.servicecomb.toolkit.CodeGenerator;

import io.swagger.codegen.DefaultGenerator;
import io.swagger.codegen.config.CodegenConfigurator;

public class DefaultCodeGenerator implements CodeGenerator {

  private DefaultGenerator generator = new DefaultGenerator();

  @Override
  public boolean canProcess(String type) {
    return "default".equals(type);
  }

  @Override
  public void configure(Map<String, Object> config) {
    CodegenConfigurator opts = (CodegenConfigurator) config.get("configurator");
    generator.setGenerateSwaggerMetadata(false);
    generator.opts(opts.toClientOptInput());
  }

  @Override
  public void generate() {
    generator.generate();
  }
}
