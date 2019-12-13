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

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
if [[ ! $VERSION =~ "SNAPSHOT" ]]; then
  echo "${green}[SCRIPT] Not Snapshot Version,Skipping Deployment.${reset}"
else
  echo "Deploy a Non-Signed Staging Release"
  mvn deploy -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B -DskipTests --settings .travis.settings.xml
  if [ $? == 0 ]; then
        echo "${green}Snapshot Deployment is Success, please log on to Nexus Repo to see the snapshot release..${reset}"
  else
        echo "${red}Snapshot deployment failed.${reset}"
  fi
  echo "Deployment Completed"
fi
