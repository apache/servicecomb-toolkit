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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class TextCompareTest {

  Path sourcePath = Paths.get("./src/test/resources/compare/HelloEndPoint.yaml");

  Path destPath = Paths.get("./src/test/resources/compare/HelloEndPoint2.yaml");

  @Test
  public void testContractCompareText() throws IOException {
    ContractComparator contractComparator = new ContractComparator(new String(Files.readAllBytes(sourcePath)),
        new String(Files.readAllBytes(destPath)));
    assertEquals(MyersAlgorithm.class, contractComparator.getAlgorithm().getClass());

    List<Comparison> comparisonList = contractComparator.compare();

    assertEquals(3, comparisonList.size());
  }

  @Test
  public void testContractCompareResultPrint() throws IOException {
    ContractComparator contractComparator = new ContractComparator(new String(Files.readAllBytes(sourcePath)),
        new String(Files.readAllBytes(destPath)));
    assertEquals(MyersAlgorithm.class, contractComparator.getAlgorithm().getClass());

    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    contractComparator.splitPrint(bout);

    assertTrue(0 < bout.toByteArray().length);
  }

  @Test
  public void testContractCompareAnotherAlgorithm() throws IOException {

    CompareAlgorithm oneLineAlgorithm = mock(CompareAlgorithm.class);
    when(oneLineAlgorithm.compare(anyString(), anyString())).then(new Answer<Object>() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        Object[] args = invocationOnMock.getArguments();
        String source = (String) args[0];
        String dest = (String) args[1];
        if ((source == null || "".equals(source.trim())) && dest != null) {
          Comparison comparison = new Comparison(ComparisionType.INSERT, 0, 0, 1, 0);
          return Collections.singletonList(comparison);
        }

        if (source != null && (dest == null || "".equals(dest.trim()))) {
          Comparison comparison = new Comparison(ComparisionType.DELETE, 0, 0, 0, 0);
          return Collections.singletonList(comparison);
        }

        if (hasNewLine(source) || hasNewLine(dest)) {
          return null;
        }

        if ((dest).equals(source)) {
          return Collections.singletonList(new Comparison(ComparisionType.EQUAL, 0, 0, 0, 0));
        }
        Comparison comparison = new Comparison(ComparisionType.REPLACE, 0, 1, 0, 1);
        return Collections.singletonList(comparison);
      }
    });

    ContractComparator contractComparator = new ContractComparator("source line",
        "destination line", oneLineAlgorithm);

    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    contractComparator.splitPrint(bout);
    bout.flush();
    assertTrue(0 < bout.toByteArray().length);

    bout = new ByteArrayOutputStream();
    contractComparator = new ContractComparator("source line",
        "source line", oneLineAlgorithm);
    contractComparator.splitPrint(bout);
    assertEquals(ComparisionType.EQUAL, contractComparator.compare().get(0).type);
  }

  private boolean hasNewLine(String s) {
    return Pattern.compile("\\r?\\n").matcher(s).find();
  }

  @Test
  public void testContractCompareException() throws IOException {

    try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {

      ContractComparator contractComparator = new ContractComparator(null,
          new String(Files.readAllBytes(destPath)));
      assertEquals(MyersAlgorithm.class, contractComparator.getAlgorithm().getClass());
      contractComparator.splitPrint(bout);
    } catch (RuntimeException e) {
      assertEquals("Source and dest must not be null", e.getMessage());
    }
  }
}
