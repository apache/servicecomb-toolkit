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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyersAlgorithm implements CompareAlgorithm {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyersAlgorithm.class);

  @Override
  public List<Comparison> compare(String source, String dest) {

    if ((source == null) || (dest == null)) {
      LOGGER.error("Source is {} and dest is {}", source, dest);
      throw new RuntimeException("Source and dest must not be null");
    }

    EditList diffList = new EditList();
    diffList.addAll(MyersDiff.INSTANCE.diff(RawTextComparator.DEFAULT,
        new RawText(source.getBytes()), new RawText(dest.getBytes())));

    List<Comparison> comparisonList = new ArrayList<>();

    diffList.stream().forEachOrdered(edit -> {
      ComparisionType comparisionType;
      switch (edit.getType()) {
        case INSERT:
          comparisionType = ComparisionType.INSERT;
          break;
        case DELETE:
          comparisionType = ComparisionType.DELETE;
          break;
        case REPLACE:
          comparisionType = ComparisionType.REPLACE;
          break;
        default:
          comparisionType = ComparisionType.EQUAL;
          break;
      }
      comparisonList
          .add(new Comparison(comparisionType, edit.getBeginA(), edit.getEndA(), edit.getBeginB(), edit.getEndB()));
    });
    return comparisonList;
  }
}
