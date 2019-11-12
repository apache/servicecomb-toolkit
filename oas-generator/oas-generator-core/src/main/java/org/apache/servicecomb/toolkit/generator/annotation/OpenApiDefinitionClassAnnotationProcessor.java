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

package org.apache.servicecomb.toolkit.generator.annotation;

import java.util.ArrayList;
import java.util.List;

import org.apache.servicecomb.toolkit.generator.context.OasContext;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

public class OpenApiDefinitionClassAnnotationProcessor implements
    ClassAnnotationProcessor<OpenAPIDefinition, OasContext> {

  @Override
  public void process(OpenAPIDefinition openAPIDefinition, OasContext context) {

    Info infoAnnotation = openAPIDefinition.info();
    io.swagger.v3.oas.models.info.Info info = new io.swagger.v3.oas.models.info.Info();
    License license = new License();
    Contact contact = new Contact();

    license.url(infoAnnotation.license().url());
    license.name(infoAnnotation.license().name());
    contact.name(infoAnnotation.contact().name());
    contact.url(infoAnnotation.contact().url());
    contact.email(infoAnnotation.contact().email());

    info
        .description(infoAnnotation.description())
        .title(infoAnnotation.title())
        .version(infoAnnotation.version())
        .license(license)
        .contact(contact);
    context.getOpenAPI().info(info);

    if (openAPIDefinition.servers() != null) {
      List<Server> serverList = new ArrayList<>();
      for (io.swagger.v3.oas.annotations.servers.Server serverAnnotation : openAPIDefinition.servers()) {
        Server server = new Server();
        server.url(serverAnnotation.url());
        server.description(serverAnnotation.description());
        serverList.add(server);
      }
      context.getOpenAPI().servers(serverList);
    }

    if (openAPIDefinition.tags() != null) {
      List<Tag> tagList = new ArrayList<>();
      for (io.swagger.v3.oas.annotations.tags.Tag tagAnnotation : openAPIDefinition.tags()) {
        Tag tag = new Tag();
        tag
            .name(tagAnnotation.name())
            .description(tagAnnotation.description());
        tagList.add(tag);
      }

      context.getOpenAPI().tags(tagList);
    }
  }
}
