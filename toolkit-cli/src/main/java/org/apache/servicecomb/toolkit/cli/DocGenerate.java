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

package org.apache.servicecomb.toolkit.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.servicecomb.toolkit.docgen.DocGeneratorManager;
import org.apache.servicecomb.swagger.SwaggerUtils;

import io.airlift.airline.Command;
import io.airlift.airline.Option;

@Command(name = "docGenerate", description = "Generate document by OpenAPI specification file")
public class DocGenerate implements Runnable {

  @Option(name = {"-i", "--input"}, title = "OpenAPI specification file", required = true,
      description = "location of the OpenAPI specification file, as URL or file (required)")
  private String specFile;


  @Option(name = {"-f", "--format"}, title = "document format", required = false,
      description = "format of document, as html or asciidoc (html by default)")
  private String format = "html";

  @Option(name = {"-o", "--output"}, title = "output directory",
      description = "location of the generated document (current dir by default)")
  private String output = "";

  @Override
  public void run() {

    try {
      Path specPath = Paths.get(specFile);

      if (Files.isDirectory(specPath)) {

        Files.walkFileTree(specPath, new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            DocGeneratorManager.generate(SwaggerUtils.parseSwagger(file.toUri().toURL()),
                output + File.separator + file.toFile().getName().substring(0, file.toFile().getName().indexOf(".")),
                "html");
            return super.visitFile(file, attrs);
          }
        });
      } else {

        DocGeneratorManager.generate(SwaggerUtils.parseSwagger(new File(specFile).toURI().toURL()),
            output + File.separator + new File(specFile).getName()
                .substring(0, new File(specFile).getName().indexOf(".")),
            format);
      }
    } catch (IOException e) {
      // command line , direct print to screen
      e.printStackTrace();
    }
  }
}
