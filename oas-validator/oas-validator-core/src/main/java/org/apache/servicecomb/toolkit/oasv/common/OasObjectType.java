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

package org.apache.servicecomb.toolkit.oasv.common;

/**
 * OpenAPI v3 Object Type
 * <p>
 * Corresponding to <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#schema">OpenAPI Specification - Schema</a>
 * </p>
 */
public enum OasObjectType {
  OPENAPI,
  INFO,
  CONTACT,
  LICENSE,
  SERVER,
  SERVER_VARIABLE,
  COMPONENTS,
  PATHS,
  PATH_ITEM,
  OPERATION,
  EXTERNAL_DOCUMENTATION,
  PARAMETER,
  REQUEST_BODY,
  MEDIA_TYPE,
  ENCODING,
  RESPONSES,
  RESPONSE,
  CALLBACK,
  EXAMPLE,
  HEADER,
  TAG,
  REFERENCE,
  SCHEMA,
  DISCRIMINATOR,
  XML,
  SECURITY_SCHEME,
  OAUTH_FLOWS,
  OAUTH_FLOW,
  LINK,
  SECURITY_REQUIREMENT
}
