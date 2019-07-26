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

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.apache.servicecomb.toolkit.CodeGenerator;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.junit.Assert;
import org.junit.Test;

import io.swagger.codegen.CodegenType;
import io.swagger.codegen.DefaultGenerator;
import io.swagger.codegen.config.CodegenConfigurator;

public class GeneratorTest {

  @Test
  public void generateProgrammingModels() throws IOException, URISyntaxException {

    generateCode("SpringMVC");
    generateCode("POJO");
    generateCode("JAX-RS");
    generateCode("SpringBoot");
  }

  private void generateCode(String programmingModel) throws IOException, URISyntaxException {

    Path tempDir = Files.createTempDirectory(null);
    Path specFilePath = Paths.get(GeneratorTest.class.getClassLoader().getResource("swagger.yaml").toURI());
    CodegenConfigurator configurator = new CodegenConfigurator();

    configurator.setLang("ServiceComb");
    configurator.setLibrary(programmingModel);
    configurator.setOutputDir(tempDir.toFile().getCanonicalPath() + "/ServiceComb");
    configurator.setInputSpec(specFilePath.toFile().getCanonicalPath());
    DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator();
    codeGenerator.configure(Collections.singletonMap("configurator", configurator));

    try {
      codeGenerator.generate();
    } catch (RuntimeException e) {
      fail();
    }

    Object internalGenerator = ReflectUtils.getProperty(codeGenerator, "generator");
    Assert.assertEquals(DefaultGenerator.class, internalGenerator.getClass());
    Object swaggerCodegenConfig = ReflectUtils.getProperty(internalGenerator, "config");
    Assert.assertEquals(ServiceCombCodegen.class, swaggerCodegenConfig.getClass());
    Assert.assertEquals("ServiceComb", ((ServiceCombCodegen) swaggerCodegenConfig).getName());
    Assert.assertEquals(CodegenType.SERVER, ((ServiceCombCodegen) swaggerCodegenConfig).getTag());

    tempDir.toFile().deleteOnExit();
  }

  @Test
  public void getCodeGeneratorInstanse() {

    CodeGenerator defaultCodeGenerator = GeneratorFactory.getGenerator(CodeGenerator.class, "default");
    Assert.assertNotNull(defaultCodeGenerator);
    Assert.assertTrue(defaultCodeGenerator.canProcess("default"));

    CodeGenerator unknownCodeGenerator = GeneratorFactory.getGenerator(CodeGenerator.class, "unknown");
    Assert.assertNull(unknownCodeGenerator);
  }
}

