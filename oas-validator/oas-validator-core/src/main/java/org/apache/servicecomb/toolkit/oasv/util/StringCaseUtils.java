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

package org.apache.servicecomb.toolkit.oasv.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public abstract class StringCaseUtils {

  private static final Pattern LOWER_CAMEL_CASE = Pattern.compile("^[a-z]+((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?$");

  private static final Pattern UPPER_CAMEL_CASE = Pattern.compile("^[A-Z]([a-z0-9]+[A-Z]?)*$");

  private static final Pattern UPPER_HYPHEN_CASE = Pattern.compile("^([A-Z][a-z0-9]*-)*([A-Z][a-z0-9]*)$");

  private StringCaseUtils() {
    // singleton
  }

  public static boolean isLowerCamelCase(String string) {
    if (StringUtils.isBlank(string)) {
      return false;
    }
    return LOWER_CAMEL_CASE.matcher(string).matches();
  }

  public static boolean isUpperCamelCase(String string) {
    if (StringUtils.isBlank(string)) {
      return false;
    }
    return UPPER_CAMEL_CASE.matcher(string).matches();
  }

  public static boolean isUpperHyphenCase(String string) {
    if (StringUtils.isBlank(string)) {
      return false;
    }
    return UPPER_HYPHEN_CASE.matcher(string).matches();
  }

  public static boolean isMatchCase(String expectedCase, String string) {
    switch (expectedCase) {
    case "upper-camel-case":
      return isUpperCamelCase(string);
    case "lower-camel-case":
      return isLowerCamelCase(string);
    case "upper-hyphen-case":
      return isUpperHyphenCase(string);
    default:
      throw new IllegalArgumentException("Unrecognized case: " + expectedCase);
    }
  }
}
