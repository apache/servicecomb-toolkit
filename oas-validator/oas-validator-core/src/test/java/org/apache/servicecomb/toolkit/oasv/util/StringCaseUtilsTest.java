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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCaseUtilsTest {

  @Test
  public void isLowerCamelCase() {

    assertThat(StringCaseUtils.isLowerCamelCase("")).isFalse();

    assertThat(StringCaseUtils.isLowerCamelCase("A")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("A0")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("Aa")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("AA")).isFalse();

    assertThat(StringCaseUtils.isLowerCamelCase("0")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("00")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("0a")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("0A")).isFalse();

    assertThat(StringCaseUtils.isLowerCamelCase("a")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("a0")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aa")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aA")).isTrue();

    assertThat(StringCaseUtils.isLowerCamelCase("a00")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("a0a")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("a0A")).isTrue();

    assertThat(StringCaseUtils.isLowerCamelCase("aa0")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aaa")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aaA")).isTrue();

    assertThat(StringCaseUtils.isLowerCamelCase("aaA0")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aaAa")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aaAA")).isFalse();

    assertThat(StringCaseUtils.isLowerCamelCase("aaA00")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aaA0a")).isTrue();
    assertThat(StringCaseUtils.isLowerCamelCase("aaA0A")).isTrue();

    assertThat(StringCaseUtils.isLowerCamelCase("_aaA00")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("a_aA00")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("aa_A00")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("aaA_00")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("aaA0_0")).isFalse();
    assertThat(StringCaseUtils.isLowerCamelCase("aaA00_")).isFalse();

  }

  @Test
  public void isUpperCamelCase() {

    assertThat(StringCaseUtils.isUpperCamelCase("")).isFalse();

    assertThat(StringCaseUtils.isUpperCamelCase("A")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("A0")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("Aa")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("AA")).isFalse();

    assertThat(StringCaseUtils.isUpperCamelCase("0")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("00")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("0a")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("0A")).isFalse();

    assertThat(StringCaseUtils.isUpperCamelCase("a")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("a0")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("aa")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("aA")).isFalse();

    assertThat(StringCaseUtils.isUpperCamelCase("A00")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("A0a")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("A0A")).isTrue();

    assertThat(StringCaseUtils.isUpperCamelCase("Aa0")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("Aaa")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("AaA")).isTrue();

    assertThat(StringCaseUtils.isUpperCamelCase("AaA0")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("AaAa")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("AaAA")).isFalse();

    assertThat(StringCaseUtils.isUpperCamelCase("AaA00")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("AaA0a")).isTrue();
    assertThat(StringCaseUtils.isUpperCamelCase("AaA0A")).isTrue();

    assertThat(StringCaseUtils.isUpperCamelCase("_aaA00")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("a_aA00")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("aa_A00")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("aaA_00")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("aaA0_0")).isFalse();
    assertThat(StringCaseUtils.isUpperCamelCase("aaA00_")).isFalse();

  }

  @Test
  public void isUpperHyphenCase() {

    assertThat(StringCaseUtils.isUpperHyphenCase("")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("A")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("Aa")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("AA")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("0")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("00")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("0a")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("0A")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("a")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("a0")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("aa")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("aA")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("A00")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0a")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0A")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("A-A")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("A-a")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A-0")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A-AA")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("AA-A")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("AA-a")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("AA-0")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("Aa-A")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("Aa-Aa")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("Aa-A0")).isTrue();

    assertThat(StringCaseUtils.isUpperHyphenCase("A0-A")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-Aa")).isTrue();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-A0")).isTrue();

    assertThat(StringCaseUtils.isUpperHyphenCase("A0-a0")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-aa")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-aA")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("A0-0")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-00")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-0a")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("A0-0A")).isFalse();

    assertThat(StringCaseUtils.isUpperHyphenCase("_aaA00")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("a_aA00")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("aa_A00")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("aaA_00")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("aaA0_0")).isFalse();
    assertThat(StringCaseUtils.isUpperHyphenCase("aaA00_")).isFalse();

  }

  @Test
  public void testIsMatchCase() {

    assertThat(StringCaseUtils.isMatchCase("lower-camel-case", "a0")).isTrue();
    assertThat(StringCaseUtils.isMatchCase("upper-camel-case", "A0")).isTrue();
    assertThat(StringCaseUtils.isMatchCase("upper-hyphen-case", "A-A")).isTrue();
  }

}
