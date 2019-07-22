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

package org.apache.servicecomb.toolkit.plugin;

import org.apache.maven.plugins.annotations.Parameter;

public class ServiceConfig {

  @Parameter(defaultValue = "all")
  private String serviceType;

  @Parameter(defaultValue = "domain.orgnization.project")
  private String groupId;

  @Parameter(defaultValue = "sample")
  private String artifactId;

  @Parameter(defaultValue = "0.1.0-SNAPSHOT")
  private String artifactVersion;

  @Parameter(defaultValue = "domain.orgnization.project.sample")
  private String packageName;

  @Parameter(defaultValue = "SpringMVC")
  private String programmingModel;

  public String getServiceType() {
    return serviceType;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getArtifactVersion() {
    return artifactVersion;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getProgrammingModel() {
    return programmingModel;
  }
}
