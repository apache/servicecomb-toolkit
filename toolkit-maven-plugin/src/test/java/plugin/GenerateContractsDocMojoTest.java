package plugin;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.plugin.GenerateContractsDocMojo;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;


public class GenerateContractsDocMojoTest {

  private static final String PLUGIN_GOAL = "generateDoc";

  private static final String TEST_PROJECT = "project-generateContractsDoc";

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();


  @Test
  public void testGenerateContractsDoc() throws Exception {
    File baseDir = this.resources.getBasedir(TEST_PROJECT);
    GenerateContractsDocMojo generateContractsDocMojo = mock(GenerateContractsDocMojo.class);
    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(baseDir + "/target/classes");
    final MavenProject project = mock(MavenProject.class);
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);

    assertNotNull(generateContractsDocMojo);
    rule.setVariableValueToObject(generateContractsDocMojo, "project", project);
    rule.setVariableValueToObject(generateContractsDocMojo, "format", ".yaml");
    assertNotNull(this.rule.getVariableValueFromObject(generateContractsDocMojo, "project"));
    assertEquals(".yaml", this.rule.getVariableValueFromObject(generateContractsDocMojo, "format"));
    rule.executeMojo(project, PLUGIN_GOAL);
    generateContractsDocMojo.execute();

  }


}
