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
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.diff.DiffFormatter;

public class SplitDiffFormatter extends DiffFormatter {

  private int radius = 3;

  private final OutputStream out;

  private static final String noNewLine = "\nthis is end of compare\n";

  private static String outputFormat = "%-20s";

  private static String numberFormat = "%-4s";

  public SplitDiffFormatter(OutputStream out) {
    super(out);
    this.out = out;
  }

  public void format(List<Comparison> comparisons, String source, String dest) throws IOException {

    outputFormat = "%-" + (getMaxLineSize(source) + 10) + "s";

    List<String> sourceLines = readStringAsLines(source);
    List<String> destLines = readStringAsLines(dest);

    for (int currentIndex = 0; currentIndex < comparisons.size(); ) {
      Comparison currentComparison = comparisons.get(currentIndex);
      final int endIndex = findOutputEndIndex(comparisons, currentIndex);
      final Comparison endComparison = comparisons.get(endIndex);

      int originalBegin = (int) Math.max(0, (long) currentComparison.originalPointBegin - radius);
      int destinationBegin = (int) Math.max(0, (long) currentComparison.destinationPointBegin - radius);
      int originalEnd = (int) Math.min(sourceLines.size(), (long) endComparison.originalPointEnd + radius);
      int destinationEnd = (int) Math.min(destLines.size(), (long) endComparison.destinationPointEnd + radius);

      writeHunkHeader(originalBegin, originalEnd, destinationBegin, destinationEnd);

      while (originalBegin < originalEnd || destinationBegin < destinationEnd) {

        if (originalBegin < currentComparison.originalPointBegin || endIndex + 1 < currentIndex) {
          writeContextLine(sourceLines, originalBegin, destLines, destinationBegin);
          originalBegin++;
          destinationBegin++;
        } else if (originalBegin < currentComparison.originalPointEnd) {

          if (destinationBegin < currentComparison.destinationPointEnd) {
            writeReplaceLine(sourceLines, originalBegin, destLines, destinationBegin);
            destinationBegin++;
          } else {
            writeRemovedLine(sourceLines, originalBegin, destLines, destinationBegin);
          }
          originalBegin++;
        } else if (destinationBegin < currentComparison.destinationPointEnd) {

          writeAddedLine(sourceLines, originalBegin, destLines, destinationBegin);
          destinationBegin++;
        }

        if (end(currentComparison, originalBegin, destinationBegin) && ++currentIndex < comparisons.size()) {
          currentComparison = comparisons.get(currentIndex);
        }
      }

      writeLine(noNewLine);
    }
  }

  public int getMaxLineSize(String text) {
    final int[] maxLen = {0};
    readStringAsLines(text).forEach(line -> {
      int temp = line.length();
      if (temp > maxLen[0]) {
        maxLen[0] = temp;
      }
    });
    return maxLen[0];
  }

  private List<String> readStringAsLines(String text) {
    return Arrays.asList(text.split("\\r?\\n"));
  }

  protected void writeReplaceLine(List<String> text, int line, List<String> textB, int bCur) throws IOException {
    writeLine(buildFormatLineLeft(text, line, " -") + buildFormatLineRight(textB, bCur, " +") + "\n");
  }

  protected void writeContextLine(List<String> text, int line, List<String> textB, int bCur) throws IOException {
    writeLine(buildFormatLineLeft(text, line, "  ") + buildFormatLineRight(textB, bCur, "  ") + "\n");
  }

  protected void writeRemovedLine(List<String> text, int line, List<String> textB, int bCur) throws IOException {
    writeLine(buildFormatLineLeft(text, line, " -") + "\n");
  }

  protected void writeAddedLine(List<String> text, int line, List<String> textB, int bCur) throws IOException {
    writeLine(
        String.format(numberFormat, " ") + String.format(outputFormat, " ") + buildFormatLineRight(textB, bCur, " +")
            + "\n");
  }

  protected void writeLine(final String content) throws IOException {
    out.write(content.getBytes());
  }

  private String buildFormatLineLeft(List<String> text, int line, String prefix) {
    return String.format(numberFormat, (line + 1)) + String.format(outputFormat, prefix + text.get(line));
  }

  private String buildFormatLineRight(List<String> text, int line, String prefix) {
    return String.format(numberFormat, (line + 1)) + String
        .format(getOutputFormat(text.get(line).length()), prefix + text.get(line));
  }

  private String getOutputFormat(int len) {
    return "%-" + len + "s";
  }

  private static boolean end(Comparison edit, int a, int b) {
    return edit.originalPointEnd <= a && edit.destinationPointEnd <= b;
  }

  private int findOutputEndIndex(List<Comparison> edits, int i) {
    int end = i + 1;
    while (end < edits.size()
        && (isOriginalEnd(edits, end) || isDestinationEnd(edits, end))) {
      end++;
    }
    return end - 1;
  }

  private boolean isOriginalEnd(List<Comparison> e, int i) {
    return e.get(i).originalPointBegin - e.get(i - 1).originalPointEnd <= 2 * radius;
  }

  private boolean isDestinationEnd(List<Comparison> e, int i) {
    return e.get(i).destinationPointBegin - e.get(i - 1).destinationPointEnd <= 2 * radius;
  }
}
