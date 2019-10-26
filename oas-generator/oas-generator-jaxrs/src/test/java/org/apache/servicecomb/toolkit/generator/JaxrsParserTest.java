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

package org.apache.servicecomb.toolkit.generator;

import javax.ws.rs.Path;

import org.apache.servicecomb.toolkit.generator.parser.JaxRsAnnotationParser;
import org.junit.Assert;
import org.junit.Test;

import io.swagger.v3.oas.models.Components;

public class JaxrsParserTest {

  @Test
  public void parseJaxrs() {

    JaxRsAnnotationParser parser = new JaxRsAnnotationParser();
    boolean canProcess = parser.canProcess(NoResource.class);
    Assert.assertEquals(false, canProcess);

    canProcess = parser.canProcess(OneResource.class);
    Assert.assertEquals(true, canProcess);

    OasContext context = new OasContext(parser);
    parser.parser(OneResource.class, context);
  }

  class NoResource {
    public String name() {
      return "no resource";
    }
  }

  @Path("/path")
  class OneResource {

    @Path("/name")
    public String name() {
      return "no resource";
    }
  }

  public static void main(String[] args) {
    OneResource.class.getMethods();
    Components components1 = new Components();
    Components components2 = new Components();

    System.out.println(components1.equals(components2));
  }
}
