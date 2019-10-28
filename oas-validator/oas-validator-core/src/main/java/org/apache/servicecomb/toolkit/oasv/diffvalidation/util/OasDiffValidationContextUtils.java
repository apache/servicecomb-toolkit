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

package org.apache.servicecomb.toolkit.oasv.diffvalidation.util;

import org.apache.servicecomb.toolkit.oasv.diffvalidation.api.OasDiffValidationContext;

public class OasDiffValidationContextUtils {

  private static final String IN_REQUEST_BODY = OasDiffValidationContextUtils.class.getName() + ".IN_REQUEST_BODY";
  private static final String IN_PARAMETER = OasDiffValidationContextUtils.class.getName() + ".IN_PARAMETER";
  private static final String IN_RESPONSE = OasDiffValidationContextUtils.class.getName() + ".IN_RESPONSE";

  public static void enterRequestBody(OasDiffValidationContext context) {
    context.setAttribute(IN_REQUEST_BODY, true);
  }

  public static void leaveRequestBody(OasDiffValidationContext context) {
    context.removeAttribute(IN_REQUEST_BODY);
  }

  public static void enterResponse(OasDiffValidationContext context) {
    context.setAttribute(IN_RESPONSE, true);
  }

  public static void leaveResponse(OasDiffValidationContext context) {
    context.removeAttribute(IN_RESPONSE);
  }

  public static void enterParameter(OasDiffValidationContext context) {
    context.setAttribute(IN_PARAMETER, true);
  }

  public static void leaveParameter(OasDiffValidationContext context) {
    context.removeAttribute(IN_PARAMETER);
  }

  public static boolean isInRequestBody(OasDiffValidationContext context) {
    return Boolean.TRUE.equals(context.getAttribute(IN_REQUEST_BODY));
  }

  public static boolean isInResponse(OasDiffValidationContext context) {
    return Boolean.TRUE.equals(context.getAttribute(IN_RESPONSE));
  }

  public static boolean isInParameter(OasDiffValidationContext context) {
    return Boolean.TRUE.equals(context.getAttribute(IN_PARAMETER));
  }

}
