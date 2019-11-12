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

import java.lang.reflect.Method;

import org.apache.servicecomb.toolkit.generator.annotation.ApiResponseMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ApiResponsesMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.OpenApiDefinitionClassAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.OperationMethodAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.ParameterAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.annotation.RequestBodyParamAnnotationProcessor;
import org.apache.servicecomb.toolkit.generator.context.OasContext;
import org.apache.servicecomb.toolkit.generator.context.OperationContext;
import org.apache.servicecomb.toolkit.generator.context.ParameterContext;
import org.apache.servicecomb.toolkit.generator.parser.AbstractAnnotationParser;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.media.StringSchema;

public class AnnotationProcessorTest {

  @Test
  public void processApiResponseAnnotation() {

    OasContext oasContext = new OasContext(null);
    OperationContext context = new OperationContext(null, oasContext);
    ApiResponseMethodAnnotationProcessor apiResProcessor = new ApiResponseMethodAnnotationProcessor();
    ApiResponse apiResponse = Mockito.mock(ApiResponse.class);
    Content[] contents = new Content[1];
    contents[0] = Mockito.mock(Content.class);
    Mockito.when(contents[0].mediaType()).thenReturn(MediaTypes.APPLICATION_JSON);
    Mockito.when(apiResponse.content()).thenReturn(contents);
    Mockito.when(apiResponse.responseCode()).thenReturn("200");
    apiResProcessor.process(apiResponse, context);

    Assert.assertNotNull(context.getApiResponses().get("200"));
    Assert.assertNull(context.getApiResponses().get("500"));
  }

  @Test
  public void processApiResponsesAnnotation() {

    OasContext oasContext = new OasContext(new AbstractAnnotationParser() {
      @Override
      public int getOrder() {
        return 0;
      }

      @Override
      public boolean canProcess(Class<?> cls) {
        return true;
      }
    });

    OperationContext context = new OperationContext(null, oasContext);
    ApiResponsesMethodAnnotationProcessor apiRessProcessor = new ApiResponsesMethodAnnotationProcessor();
    ApiResponses apiResponses = Mockito.mock(ApiResponses.class);
    Content[] contents = new Content[1];
    contents[0] = Mockito.mock(Content.class);
    Mockito.when(contents[0].mediaType()).thenReturn(MediaTypes.APPLICATION_JSON);
    ApiResponse apiResponse = Mockito.mock(ApiResponse.class);
    Mockito.when(apiResponse.content()).thenReturn(contents);
    Mockito.when(apiResponse.responseCode()).thenReturn("200");
    Mockito.when(apiResponses.value()).thenReturn(new ApiResponse[] {apiResponse});

    apiRessProcessor.process(apiResponses, context);

    Assert.assertNotNull(context.getApiResponses().get("200"));
    Assert.assertNull(context.getApiResponses().get("500"));
  }

  @Test
  public void processOperationAnnotation() throws NoSuchMethodException {

    OasContext oasContext = null;

    OperationMethodAnnotationProcessor operationMethodAnnotationProcessor = new OperationMethodAnnotationProcessor();

    Method helloMethod = OpenapiDef.class.getDeclaredMethod("hello", String.class, Object.class);
    Operation operation = helloMethod.getAnnotation(Operation.class);
    oasContext = new OasContext(new AbstractAnnotationParser() {
      @Override
      public int getOrder() {
        return 0;
      }

      @Override
      public boolean canProcess(Class<?> cls) {
        return true;
      }
    });
    OperationContext context = new OperationContext(helloMethod, oasContext);
    operationMethodAnnotationProcessor.process(operation, context);
    Assert.assertEquals("hello-operation", context.getOperationId());
  }

  @Test
  public void processOpenApiDefinitionClassAnnotation() throws NoSuchMethodException {

    OasContext oasContext = null;

    OpenApiDefinitionClassAnnotationProcessor openApiDefinitionClassAnnotationProcessor = new OpenApiDefinitionClassAnnotationProcessor();

    OpenAPIDefinition openAPIDefinition = OpenapiDef.class.getAnnotation(OpenAPIDefinition.class);
    oasContext = new OasContext(new AbstractAnnotationParser() {
      @Override
      public int getOrder() {
        return 0;
      }

      @Override
      public boolean canProcess(Class<?> cls) {
        return true;
      }
    });
    openApiDefinitionClassAnnotationProcessor.process(openAPIDefinition, oasContext);
//    Assert.assertEquals("hello-operation", context.getOperationId());
  }

  @Test
  public void processRequestBodyAnnotation() throws NoSuchMethodException {

    OasContext oasContext = new OasContext(null);
    RequestBodyParamAnnotationProcessor operationMethodAnnotationProcessor = new RequestBodyParamAnnotationProcessor();

    RequestBody requestBody = Mockito.mock(RequestBody.class);
    Mockito.when(requestBody.content()).thenReturn(new Content[] {Mockito.mock(Content.class)});
    Mockito.when(requestBody.ref()).thenReturn("#components/string");

    Method helloMethod = OpenapiDef.class.getDeclaredMethod("hello", String.class, Object.class);
    OperationContext operationContext = new OperationContext(helloMethod, oasContext);
    ParameterContext parameterContext = new ParameterContext(operationContext, null);
    operationMethodAnnotationProcessor.process(requestBody, parameterContext);

    Assert.assertTrue(parameterContext.isRequestBody());
  }

  @Test
  public void processParameterAnnotation() throws NoSuchMethodException, IllegalAccessException,
      InstantiationException {

    OasContext oasContext = new OasContext(null);
    Method parameterMethod = ParameterClass.class.getMethod("parameter", String.class);
    OperationContext operationContext = new OperationContext(parameterMethod, oasContext);
    java.lang.reflect.Parameter[] parameters = parameterMethod.getParameters();
    Assert.assertEquals(parameters.length, 1);
    java.lang.reflect.Parameter parameter = parameters[0];
    Parameter parameterDeclaredAnnotation = parameter.getDeclaredAnnotation(Parameter.class);

    ParameterContext parameterContext = new ParameterContext(operationContext, parameter);
    ParameterAnnotationProcessor parameterAnnotationProcessor = new ParameterAnnotationProcessor();

    parameterAnnotationProcessor.process(parameterDeclaredAnnotation, parameterContext);
    io.swagger.v3.oas.models.parameters.Parameter oasParameter = parameterContext.toParameter();
    Assert.assertEquals("param", oasParameter.getName());
    Assert.assertEquals(StringSchema.class, oasParameter.getSchema().getClass());
    Assert.assertTrue(parameterContext.isRequired());
    Assert.assertEquals(operationContext, parameterContext.getOperationContext());
    Assert.assertNull(parameterContext.getDefaultValue());
  }

  class ParameterClass {
    public void parameter(@Parameter(required = true) String param) {
    }
  }

  @OpenAPIDefinition(
      info = @Info(
          title = "openapi definition",
          description = "test openapi definition",
          termsOfService = "",
          contact = @Contact(
              name = "developer",
              url = "developer.com",
              email = "developer@developer.com",
              extensions = {
                  @Extension(
                      name = "ext1",
                      properties = {
                          @ExtensionProperty(
                              name = "k1",
                              value = "v1",
                              parseValue = true
                          )
                      })
              }
          ),
          license = @License(
              name = "Apache License v2.0",
              url = "",
              extensions = {
                  @Extension(
                      name = "ext2",
                      properties = {
                          @ExtensionProperty(
                              name = "k1",
                              value = "v1",
                              parseValue = true
                          )
                      }
                  )
              }
          ),
          version = "0.2.0",
          extensions = {
              @Extension(
                  name = "ext3",
                  properties = {
                      @ExtensionProperty(
                          name = "k1",
                          value = "v2"
                      )
                  }
              )
          }
      ),
      tags = {
          @Tag(
              name = "",
              description = "",
              externalDocs = @ExternalDocumentation(
                  description = "",
                  url = "",
                  extensions = {
                      @Extension(
                          name = "ext4",
                          properties = {
                              @ExtensionProperty(
                                  name = "k1",
                                  value = "v2"
                              )
                          }
                      )
                  }
              )
          )
      },
      servers = {
          @Server(
              url = "http://localhost",
              description = "",
              variables = {
                  @ServerVariable(
                      name = "",
                      description = "",
                      defaultValue = "",
                      allowableValues = {},
                      extensions = {
                          @Extension(
                              name = "",
                              properties = @ExtensionProperty(
                                  name = "",
                                  value = "",
                                  parseValue = true
                              )
                          )
                      }
                  )
              }
          )
      },
      security = {},
      externalDocs = @ExternalDocumentation(
          description = "",
          url = "",
          extensions = {
              @Extension(
                  name = "",
                  properties = @ExtensionProperty(
                      name = "",
                      value = "",
                      parseValue = true
                  )
              )
          }
      ),
      extensions = {
          @Extension(
              name = "",
              properties = @ExtensionProperty(
                  name = "",
                  value = "",
                  parseValue = true
              )
          )
      }
  )
  class OpenapiDef {

    @Operation(
        operationId = "hello-operation",
        method = "GET",
        tags = {
            "OpenapiDef"
        },
        summary = "",
        requestBody = @RequestBody(

        ),
        externalDocs = @ExternalDocumentation(),
        parameters = {
            @Parameter(
                name = "name123",
                in = ParameterIn.QUERY,
                description = "user name",
                required = true,
                deprecated = false,
                allowEmptyValue = true,
                style = ParameterStyle.SIMPLE,
                explode = Explode.DEFAULT,
                allowReserved = true,
                // Ignored if the properties content or array are specified
                schema = @Schema(
                    type = "string"
                ),
                hidden = false,
                example = "",
                examples = {
                    @ExampleObject
                },
                extensions = {},
                ref = ""
            )
        },
        responses = {
            @ApiResponse(
                description = "success",
                responseCode = "200",
                headers = {
                    @Header(name = "Content-Type",
                        description = "content type",
                        schema = @Schema(
                            implementation = String.class
                        ),
                        required = true,
                        deprecated = false
                    )
                },
                links = {
                    @Link()
                },
                content = {
                    @Content()
                },
                extensions = {
                    @Extension(
                        name = "",
                        properties = {}
                    )
                }

            )
        },
        deprecated = false,
        security = {
            @SecurityRequirement(
                name = "oauth2",
                scopes = {
                    "app1"
                }
            )
        },
        servers = {
            @Server(
                url = "http://localhost:8080/hello"
            )
        },
        extensions = {
            @Extension(
                name = "",
                properties = {}
            )
        },
        hidden = false,
        ignoreJsonView = false
    )
    public String hello(String name, Object notParameter) {
      return "hello";
    }
  }
}
