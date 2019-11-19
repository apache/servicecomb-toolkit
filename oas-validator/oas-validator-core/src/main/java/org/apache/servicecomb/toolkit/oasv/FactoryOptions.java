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
