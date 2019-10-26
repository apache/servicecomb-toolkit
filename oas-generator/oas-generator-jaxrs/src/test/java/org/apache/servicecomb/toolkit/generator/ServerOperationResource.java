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

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("")
public class ServerOperationResource {

  @Path("/serversoperation")
  @GET
  @ApiResponse(
      responseCode = "500",
      description = "voila!",
      content = {
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = String.class)
          ),
          @Content(
              mediaType = "application/xml",
              schema = @Schema(implementation = String.class)
          )
      })
  @ApiResponse(responseCode = "400")
  @ApiResponse(responseCode = "404", description = "User not found")
  @Operation(operationId = "getPet", description = "Pets Example")
  public ResponseBean getPet() {
    return new ResponseBean();
  }


  @Path("/serversoperation2")
  @POST
  @Operation(operationId = "getPet2", description = "Pets Example")
  public String getPet2(@Parameter(required = true) @QueryParam("pet") String pet) {
    return "";
  }


  @Path("/serversoperation3")
  @POST
  @Operation(operationId = "getPet3", description = "Pets Example")
  public String getPet3(String[] pet1, String pet2, RequestBean[] requestBean) {
    return "";
  }

  @Path("/serversoperation4")
  @POST
  @Operation(operationId = "getPet4", description = "Pets Example")
  public String getPet4(String[] pet1, RequestBean responseBean) {
    return "";
  }

  public String noResource() {
    return "no resource";
  }

  class RequestBean {

    private String name;

    private String content;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }
  }

  class ResponseBean {

    private String status;

    private String content;

    private RequestBean request;

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public RequestBean getRequest() {
      return request;
    }

    public void setRequest(RequestBean request) {
      this.request = request;
    }
  }
}
