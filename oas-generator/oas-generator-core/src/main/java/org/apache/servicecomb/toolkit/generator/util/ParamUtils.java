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

package org.apache.servicecomb.toolkit.generator.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ParamUtils {

  private static final String STATIC_CLASS_INIT = "<clinit>";

  public static final Map<Method, String[]> paramterCache = new HashMap();

  public static String getParamterName(Method method, Parameter parameter) {

    String[] parameterNames = paramterCache.get(method);

    if (parameterNames == null) {
      parameterNames = initParamterNames(method);
      if (parameterNames == null) {
        return null;
      }
    }

    int paramIndex = getParamIndex(method, parameter);
    if (paramIndex >= 0) {
      return parameterNames[paramIndex];
    }

    return null;
  }

  private static int getParamIndex(Method method, Parameter parameter) {
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i].equals(parameter)) {
        return i;
      }
    }
    return -1;
  }

  private static String[] initParamterNames(Method m) {

    boolean isStatic = Modifier.isStatic(m.getModifiers());
    String[] paramterNames = new String[m.getParameterCount()];

    try {

      String className = m.getDeclaringClass().getName();
      String classRawName = className.replace('.', '/') + ".class";

      InputStream is = null;
      ClassLoader classLoader = m.getDeclaringClass().getClassLoader();
      if (classLoader != null) {
        is = classLoader.getResourceAsStream(classRawName);
      } else {
        is = m.getDeclaringClass().getResourceAsStream(classRawName);
      }

      if (is == null) {
        return null;
      }
      ClassReader clsReader = new ClassReader(is);
      ClassWriter clsWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

      clsReader.accept(new ClassVisitor(Opcodes.ASM7, clsWriter) {
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
            String[] exceptions) {
          MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
          if (!m.getName().equals(name) || !descriptor.equals(Type.getMethodDescriptor(m))) {
            return methodVisitor;
          }

          if (!isSyntheticOrBridged(access) && !STATIC_CLASS_INIT.equals(name)) {
            return new LocalVariableVisitor(this.api, descriptor, isStatic, paramterNames);
          }

          return methodVisitor;
        }
      }, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }

    paramterCache.put(m, paramterNames);

    return paramterNames;
  }

  private static boolean isSyntheticOrBridged(int access) {
    return (((access & Opcodes.ACC_SYNTHETIC) | (access & Opcodes.ACC_BRIDGE)) > 0);
  }
}
