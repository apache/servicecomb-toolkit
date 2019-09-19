package org.apache.servicecomb.toolkit.codegen;

import org.junit.Assert;
import org.junit.Test;

import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenConfigLoader;

public class SpringCloudCodegenTest {

  @Test
  public void testLoadImpl() {
    CodegenConfig codegenConfig = CodegenConfigLoader.forName("SpringCloud");
    Assert.assertEquals(SpringCloudCodegen.class, codegenConfig.getClass());
  }
}
