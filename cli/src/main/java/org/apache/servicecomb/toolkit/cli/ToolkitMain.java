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

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;

public class ToolkitMain {

  private static String projectVersion;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    initialProjectVersion();

    String scriptName = System.getProperty("script.name");
    Cli.CliBuilder<Runnable> builder = null;
    if (StringUtils.isNotEmpty(scriptName)) {
      builder = Cli.builder(scriptName);
    } else {
      builder = Cli.builder("java -jar cli-" + projectVersion + ".jar");
    }

    builder.withDescription("Microservice development toolkit(version " + projectVersion
        + "). ");
    builder.withDefaultCommand(Help.class);
    builder.withCommands(
        CodeGenerate.class, DocGenerate.class,
        CheckStyle.class, CheckStyleAbbr.class,
        CheckCompatibility.class, CheckCompatibilityAbbr.class,
        Help.class
    );
    try {
      Runnable cmd = builder.build().parse(args);

      cmd.run();
    } catch (ValidationFailedException ex) {
      ex.printStackTrace(System.err);
      System.exit(1);
    }
  }

  private static void initialProjectVersion() {

    Properties properties = new Properties();
    try {
      properties.load(ToolkitMain.class.getClassLoader().getResourceAsStream("application.properties"));

      projectVersion = Optional.ofNullable(properties.getProperty("version")).orElse("unknown");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
