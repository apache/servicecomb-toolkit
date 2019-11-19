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

package org.apache.servicecomb.toolkit.oasv;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class FactoryOptions {

  private final Map<String, String> options;

  public FactoryOptions(Map<String, String> options) {
    this.options = Collections.unmodifiableMap(new HashMap<>(options));
  }

  public FactoryOptions(Properties properties) {
    Map<String, String> options = new HashMap<>();
    Set<Object> keys = properties.keySet();
    for (Object key : keys) {
      options.put((String) key, (String) properties.get(key));
    }
    this.options = Collections.unmodifiableMap(options);
  }

  public String getString(String key) {
    String value = options.get(key);
    if (StringUtils.isNotBlank(value)) {
      return value;
    }
    return null;
  }

  public Boolean getBoolean(String key) {
    String value = options.get(key);
    if (StringUtils.isNotBlank(value)) {
      return Boolean.valueOf(value);
    }
    return null;
  }

  public Integer getInteger(String key) {
    String value = options.get(key);
    if (StringUtils.isNotBlank(value)) {
      return Integer.valueOf(value);
    }
    return null;
  }

}
