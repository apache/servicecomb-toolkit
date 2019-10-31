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

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.openapitools.codegen.SupportingFile;

public abstract class AbstractDirectoryStrategy implements DirectoryStrategy<List<SupportingFile>> {

  protected Map<String, Object> propertiesMap = Collections.emptyMap();

  protected String projectFolder = "src" + File.separator + "main";

  protected String sourceFolder = projectFolder + File.separator + "java";

  @Override
  public void addCustomProperties(Map<String, Object> propertiesMap) {
    this.propertiesMap = propertiesMap;
  }

  protected String mainClassFolder(String projectPath) {
    return projectPath + File.separator + sourceFolder + File.separator + ((String) propertiesMap
        .get("mainClassPackage")).replace(".", File.separator);
  }

  protected String resourcesFolder(String projectPath) {
    return projectPath + File.separator + projectFolder + File.separator + "resources";
  }
}
