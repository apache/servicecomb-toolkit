/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.toolkit.sample.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class BookingController {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Value("${car.service.address:http://localhost:8081}")
  private String carServiceUrl;

  @Value("${hotel.service.address:http://localhost:8082}")
  private String hotelServiceUrl;

  @Autowired
  private RestTemplate template;

  @PostMapping("/booking/{name}/{rooms}/{cars}")
  public String order(@PathVariable String name,  @PathVariable Integer rooms, @PathVariable Integer cars) {
    template.postForEntity(
        carServiceUrl + "/order/{name}/{cars}",
        null, String.class, name, cars);

    template.postForEntity(
        hotelServiceUrl + "/order/{name}/{rooms}",
          null, String.class, name, rooms);

    return name + " booking " + rooms + " rooms and " + cars + " cars OK";
  }

}
