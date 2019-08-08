package org.apache.servicecomb.toolkit.plugin;

import org.apache.maven.project.MavenProject;

public class MavenPluginUtil {

  private static final String PARENT_PROJECT_PACKAGING = "pom";

  public static boolean isParentProject(MavenProject project){
    return PARENT_PROJECT_PACKAGING.equals(project.getPackaging());
  }

}
