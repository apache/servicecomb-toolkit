package org.apache.servicecomb.toolkit.oasv.compliance.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.servicecomb.toolkit.oasv.validation.api.OpenApiValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ValidatorFactoryTestConfiguration.class)
public class DefaultOpenApiValidatorFactoryTest {

  @Autowired
  private DefaultOpenApiValidatorFactory validatorFactory;

  @Test
  public void create() {
    List<OpenApiValidator> validators = validatorFactory.create();
    assertThat(validators).hasSize(8);
  }

}
