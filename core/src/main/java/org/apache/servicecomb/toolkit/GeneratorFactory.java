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

package org.apache.servicecomb.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratorFactory {

  private final static Map<String, List> interfaceMap = new ConcurrentHashMap<>();

  static {
    addGeneratorType(CodeGenerator.class);
    addGeneratorType(DocGenerator.class);
    addGeneratorType(ContractsGenerator.class);
  }

  public static void addGeneratorType(Class<? extends Generator> generatorClass) {

    if (interfaceMap.get(generatorClass.getName()) == null) {
      interfaceMap.put(generatorClass.getName(), loadInitialCodeGenerators(generatorClass));
    }
  }

  private static List<Generator> loadInitialCodeGenerators(Class<? extends Generator> generatorClass) {
    List<Generator> registeredGenerators = new ArrayList<>();
    ServiceLoader.load(generatorClass).forEach(registeredGenerators::add);
    return registeredGenerators;
  }

  public static <T extends Generator> T getGenerator(Class<? extends T> generatorClass, String type) {

    @SuppressWarnings("unchecked")
    List<T> registeredList = (List<T>) interfaceMap.get(generatorClass.getName());
    for (T generator : registeredList) {
      if (generator.canProcess(type)) {
        return generator;
      }
    }

    return null;
  }
}
