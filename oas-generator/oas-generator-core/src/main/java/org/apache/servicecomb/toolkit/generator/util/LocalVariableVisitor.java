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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class LocalVariableVisitor extends MethodVisitor {

  private boolean isStatic;

  private String[] parameterNames;

  private final int[] lvtSlotIndex;

  private final Type[] args;

  public LocalVariableVisitor(int api, String desc, boolean isStatic, String[] parameterNames) {
    super(api);
    this.isStatic = isStatic;
    this.parameterNames = parameterNames;
    this.args = Type.getArgumentTypes(desc);
    this.lvtSlotIndex = computeLvtSlotIndices(isStatic, this.args);
  }

  @Override
  public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end,
      int index) {

    for (int i = 0; i < this.lvtSlotIndex.length; i++) {
      if (this.lvtSlotIndex[i] == index) {
        this.parameterNames[i] = name;
      }
    }
    super.visitLocalVariable(name, descriptor, signature, start, end, index);
  }

  int[] computeLvtSlotIndices(boolean isStatic, Type[] paramTypes) {
    int[] lvtIndex = new int[paramTypes.length];
    int nextIndex = (isStatic ? 0 : 1);
    for (int i = 0; i < paramTypes.length; i++) {
      lvtIndex[i] = nextIndex;
      if (isWideType(paramTypes[i])) {
        nextIndex += 2;
      } else {
        nextIndex++;
      }
    }
    return lvtIndex;
  }

  private boolean isWideType(Type aType) {
    return (aType == Type.LONG_TYPE || aType == Type.DOUBLE_TYPE);
  }
}
