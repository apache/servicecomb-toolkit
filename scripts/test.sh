#!/usr/bin/env bash
## ---------------------------------------------------------------------------
## Licensed to the Apache Software Foundation (ASF) under one or more
## contributor license agreements.  See the NOTICE file distributed with
## this work for additional information regarding copyright ownership.
## The ASF licenses this file to You under the Apache License, Version 2.0
## (the "License"); you may not use this file except in compliance with
## the License.  You may obtain a copy of the License at
##
##      http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
## ---------------------------------------------------------------------------
#bin/sh

##Check if the commit is tagged commit or not
TAGGEDCOMMIT=$(git tag -l --contains HEAD)
if [ "$TAGGEDCOMMIT" == "" ]; then
  TAGGEDCOMMIT=false
else
  TAGGEDCOMMIT=true
fi
echo "${green}[SCRIPT] TAGGEDCOMMIT=$TAGGEDCOMMIT${reset}"

if [ "$TAGGEDCOMMIT" == "true" ]; then
  echo "${green}[SCRIPT] Skipping the installation as it is tagged commit.${reset}"
else
  mvn apache-rat:check
  if [ $? != 0 ]; then
    echo "${red}[SCRIPT] Rat check failed.${reset}"
    exit 1
  fi
  
  MVN_CMD="clean install -Pjacoco"
  
  if [ -n "$TRAVIS_JOB_ID" ]; then
    echo "${green}[SCRIPT] It's a travis job.${reset}"
    MVN_CMD="$MVN_CMD coveralls:report"
  else
    echo "${green}[SCRIPT] It's not a travis job.${reset}"
  fi
  
  echo "${green}[SCRIPT] TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST${reset}"
  if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
    echo "${green}[SCRIPT] It's not a PR build, enable sonar.${reset}"
    MVN_CMD="$MVN_CMD sonar:sonar -Dsonar.projectKey=servicecomb-toolkit"
  else
    echo "${green}[SCRIPT] It's a PR build or local build.${reset}"
  fi;
  
  echo "${green}[SCRIPT] Running unit and integration tests.${reset}"
  mvn $MVN_CMD
  
  if [ $? == 0 ]; then
    echo "${green}[SCRIPT] Build success.${reset}"
  else
    echo "${red}[SCRIPT] Build or tests failed, please check the logs for more details.${reset}"
    exit 1
  fi
fi