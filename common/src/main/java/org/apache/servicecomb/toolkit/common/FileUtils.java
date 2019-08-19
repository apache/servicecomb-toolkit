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
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileUtils {

  public static void createDirectory(String pathName) throws IOException {

    if (pathName == null) {
      throw new IOException("Path is null");
    }

    File path = new File(pathName);
    if (path.exists()) {
      return;
    }

    if (!path.mkdirs()) {
      throw new IOException("Failed to create directory");
    }
  }

  public static Path createTempDirectory(String pathName) throws IOException {

    createDirectory((pathName));

    return Files.createTempDirectory(Paths.get(new File(pathName).toURI()), "");
  }

  public static Map<String, byte[]> getFilesGroupByFilename(String pathName) throws IOException {

    if (pathName == null) {
      throw new IOException("Path is null");
    }

    if (!new File(pathName).exists()) {
      throw new IOException("Path " + pathName + " is not exists");
    }

    Map<String, byte[]> filesGroup = new HashMap<>();
    File path = new File(pathName);

    Files.walkFileTree(Paths.get(path.toURI()), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        filesGroup.put(file.toFile().getName(), Files.readAllBytes(file));
        return super.visitFile(file, attrs);
      }
    });

    return filesGroup;
  }

  public static void deleteDirectory(String pathName) throws IOException {

    File path = new File(pathName);

    if (!path.isDirectory()) {
      if(path.exists()){
        Files.delete(Paths.get(pathName));
      }
      return;
    }

    File[] files = path.listFiles();

    for (File file : Objects.requireNonNull(files)) {
      deleteDirectory(file.getCanonicalPath());
    }

    Files.delete(Paths.get(pathName));
  }
}
