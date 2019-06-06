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

import org.apache.servicecomb.swagger.SwaggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.Swagger;

public class ContractsUtils {

  private static Logger LOGGER = LoggerFactory.getLogger(ContractsUtils.class);

  public static Map<String, Swagger> getContractsFromFileSystem(String dir) throws IOException {

    Map<String, Swagger> contracts = new HashMap<>();
    File outputDir = new File(dir);

    Files.walkFileTree(Paths.get(outputDir.toURI()), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        contracts.put(file.toFile().getName(), SwaggerUtils.parseSwagger(file.toUri().toURL()));
        return super.visitFile(file, attrs);
      }
    });

    return contracts;
  }

  public static Map<String, byte[]> getFilesGroupByFilename(String dir) throws IOException {

    Map<String, byte[]> contracts = new HashMap<>();
    File outputDir = new File(dir);

    Files.walkFileTree(Paths.get(outputDir.toURI()), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        contracts.put(file.toFile().getName(), Files.readAllBytes(file));
        return super.visitFile(file, attrs);
      }
    });

    return contracts;
  }
}
