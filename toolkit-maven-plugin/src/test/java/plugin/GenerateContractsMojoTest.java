package plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.plugin.GenerateContractsMojo;
import org.junit.Rule;
import org.junit.Test;

public class GenerateContractsMojoTest {

  private static final String PLUGIN_GOAL = "generateContracts";

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testGenerateContracts() throws Exception {
    executeMojo("project-generateContracts", PLUGIN_GOAL);
  }

  protected void executeMojo(String projectName, String goalName) throws Exception {

    File baseDir = this.resources.getBasedir(projectName);
    assertNotNull(baseDir);
    assertTrue(baseDir.exists());
    assertTrue(baseDir.isDirectory());

    File pom = new File(baseDir, "pom.xml");
    AbstractMojo generateContractsMojo = (AbstractMojo) this.rule.lookupMojo(goalName, pom);

    assertNotNull(generateContractsMojo);
    assertEquals(GenerateContractsMojo.class, generateContractsMojo.getClass());

    final MavenProject project = mock(MavenProject.class);
    given(project.getFile()).willReturn(pom);
    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(baseDir + "/classes");
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);

    rule.setVariableValueToObject(generateContractsMojo, "project", project);
    assertNotNull(this.rule.getVariableValueFromObject(generateContractsMojo, "project"));

    assertEquals("target/test_output_contracts",
        this.rule.getVariableValueFromObject(generateContractsMojo, "outputDir"));
    assertEquals(".yaml", this.rule.getVariableValueFromObject(generateContractsMojo, "format"));

    generateContractsMojo.execute();

    assertTrue(new File("target/test_output_contracts").exists());
  }
}
