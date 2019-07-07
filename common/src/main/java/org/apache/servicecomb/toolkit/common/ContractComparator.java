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

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractComparator {

  private final static Logger LOGGER = LoggerFactory.getLogger(ContractComparator.class);

  private CompareAlgorithm algorithm;

  private String source;

  private String dest;

  private List<Comparison> comparisonList;

  public ContractComparator(String source, String dest) {
    this(source, dest, new MyersAlgorithm());
  }

  public ContractComparator(String source, String dest, CompareAlgorithm algorithm) {
    this.source = source;
    this.dest = dest;
    this.algorithm = algorithm;
    this.comparisonList = compare();
  }

  public void setAlgorithm(CompareAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  public CompareAlgorithm getAlgorithm() {
    return algorithm;
  }

  public List<Comparison> compare() {
    return algorithm.compare(source, dest);
  }

  public boolean equals() {
    if (comparisonList.size() > 0) {
      return false;
    } else {
      return true;
    }
  }

  public void splitPrintToScreen() {
    splitPrint(System.out);
  }

  public void splitPrint(OutputStream outputStream) {
    try {
      new SplitDiffFormatter(outputStream).format(comparisonList, source, dest);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
