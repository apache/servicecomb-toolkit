package org.apache.servicecomb.toolkit.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.apache.maven.project.MavenProject;
import org.junit.Test;

public class MavenPluginUtilTest {

  @Test
  public void isParentMavenProject() {

    MavenProject mavenProject = mock(MavenProject.class);
    given(mavenProject.getPackaging()).willReturn("pom");
    assertEquals(mavenProject.getPackaging(), "pom");
    assertTrue(MavenPluginUtil.isParentProject(mavenProject));
  }

  @Test
  public void notParentMavenProject() {

    MavenProject mavenProject = mock(MavenProject.class);
    given(mavenProject.getPackaging()).willReturn("jar");
    assertEquals(mavenProject.getPackaging(), "jar");
    assertFalse(MavenPluginUtil.isParentProject(mavenProject));
  }
}
