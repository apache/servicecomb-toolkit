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

package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.IOUtils;

public class ClassMaker {

  public static void compile(String projectPath) throws IOException, TimeoutException, InterruptedException {
    Runtime runtime = Runtime.getRuntime();
    Process exec = runtime.exec("mvn clean package -f " + projectPath);

    Worker worker = new Worker(exec);
    worker.start();
    ProcessStatus ps = worker.getProcessStatus();

    try {
      worker.join(30000);
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
        InputStream is = process.getInputStream();
        try {
          ps.output = IOUtils.toString(is);
        } catch (IOException ignore) {
        }
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

    volatile int exitCode;

    volatile String output;
  }
}
