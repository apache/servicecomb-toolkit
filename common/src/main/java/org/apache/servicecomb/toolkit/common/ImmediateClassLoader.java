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

package org.apache.servicecomb.toolkit.common;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmediateClassLoader extends URLClassLoader {

  private final static Logger LOGGER = LoggerFactory.getLogger(ImmediateClassLoader.class);

  private String classPath;

  public ImmediateClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
    for (URL url : urls) {
      if (url != null) {
        File file = new File(url.getPath());
        classPath = file.getAbsolutePath();
        scanClassFile(file);
      }
    }
  }

  private void scanClassFile(File file) {
    if (file.exists()) {
      if (file.isFile() && file.getName().endsWith(".class")) {
        try {
          String className = file.getAbsolutePath().replace(classPath + File.separator, "")
              .replace(File.separator, ".")
              .replace(".class", "");
          loadClass(className);
        } catch (ClassNotFoundException e) {
          LOGGER.error(e.getMessage());
        }
      } else if (file.isDirectory()) {
        for (File f : file.listFiles()) {
          scanClassFile(f);
        }
      }
    }
  }
}
