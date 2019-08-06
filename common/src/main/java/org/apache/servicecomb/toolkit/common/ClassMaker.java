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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassMaker {

  public static void compile(String projectPath) throws IOException, TimeoutException, InterruptedException {
    Runtime runtime = Runtime.getRuntime();
    String mvnCommand =
        "mvn clean package -f " + projectPath;
    Process exec = runtime
        .exec(mvnCommand);

    Worker worker = new Worker(exec);
    worker.start();
    ProcessStatus ps = worker.getProcessStatus();

    try {
      worker.join(30000);
      if (ps.exitCode == ProcessStatus.CODE_FAIL) {
        throw new RuntimeException("Command exec fail,command is: " + mvnCommand);
      }
      if (ps.exitCode == ProcessStatus.CODE_STARTED) {
        // not finished
        worker.interrupt();
        throw new TimeoutException();
      }
    } catch (InterruptedException e) {
      // canceled by other thread.
      worker.interrupt();
      throw e;
    }
  }

  private static class Worker extends Thread {
    private final Process process;

    private ProcessStatus ps;

    private Worker(Process process) {
      this.process = process;
      this.ps = new ProcessStatus();
    }

    @Override
    public void run() {
      try {
        new ExecReader(process.getInputStream(), "Command Exec Result Reader") {
          @Override
          public void afterReadLine(String line) {
            LOGGER.info(line);
          }
        }.start();

        new ExecReader(process.getErrorStream(), "Command Exec Error Reader") {
          @Override
          public void afterReadLine(String line) {
            LOGGER.error(line);
          }
        }.start();

        ps.exitCode = process.waitFor();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    ProcessStatus getProcessStatus() {
      return this.ps;
    }
  }

  public static class ProcessStatus {
    static final int CODE_STARTED = -257;

    static final int CODE_FAIL = 1;

    volatile int exitCode;

    volatile String output;
  }

  private static abstract class ExecReader extends Thread {

    public final Logger LOGGER;

    private final InputStream is;

    private final String readerName;

    public ExecReader(InputStream is, String readerName) {
      this.is = is;
      this.readerName = readerName;
      LOGGER = LoggerFactory.getLogger(ExecReader.class.getName() + "-" + readerName);
    }

    @Override
    public void run() {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = null;
      try {

        while ((line = reader.readLine()) != null) {
          afterReadLine(line);
        }
      } catch (IOException e) {
        LOGGER.error(readerName, e);
      }
    }

    public abstract void afterReadLine(String line);
  }
}
