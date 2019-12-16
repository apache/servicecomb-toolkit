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

projectDir=$(pwd)

# get an binary package
mvn clean install -DskipTests -Prelease -Dgpg.skip=true
cd toolkit-distribution/target/
unzip apache-servicecomb-toolkit-distribution-*-bin.zip
cd apache-servicecomb-toolkit-distribution-*-bin

function checkFail
{
  if [ $? -ne 0 ]; then
    echo "run $1 failed"
    exit 1
  fi
}

function helpTest
{
  # help
  ./cli.sh help

  checkFail helpTest
  echo "./cli.sh help test success"
}

function codegenerateTest
{
  # codegenerate

  for programingModel in "SpringMVC" "POJO" "JAX-RS" "SpringBoot"
  do
    ./cli.sh codegenerate \
     -m ServiceComb \
     -i ${projectDir}/cli/src/test/resources/swagger.yaml \
     -o ${projectDir}/target \
     -p ${programingModel} 2>&1 \
     | grep "Success to generate code"

    checkFail codegenerateTest
    echo "generate ${programingModel} is ok"
  done

  echo "./cli.sh codegenerate test success"
}

function docgenerateTest
{
  # docgenerate
  ./cli.sh docgenerate \
   -i ${projectDir}/cli/src/test/resources/swagger.yaml \
   -o ${projectDir}/target 2>&1

  checkFail docgenerateTest
  echo "./cli.sh docgenerate test success"
}

function checkstyleTest
{
  # checkstyle
  ./cli.sh checkstyle \
   -r ${projectDir}/cli/src/test/resources/oas/style-rules.properties \
   -f ${projectDir}/cli/src/test/resources/oas/style.yaml 2>&1 \
   | grep "check not passed"

  checkFail checkstyleTest

  ./cli.sh cs \
   -r ${projectDir}/cli/src/test/resources/oas/style-rules.properties \
   -f ${projectDir}/cli/src/test/resources/oas/style.yaml 2>&1 \
   | grep "check not passed"

  checkFail checkstyleTest

  echo "./cli.sh checkstyle test success"
}

function checkcompatibilityTest
{
  # checkcompatibility
  ./cli.sh checkcompatibility \
   ${projectDir}/cli/src/test/resources/oas/compatibility-left.yaml \
   ${projectDir}/cli/src/test/resources/oas/compatibility-right.yaml 2>&1 \
   | grep "adding is not allowed on right side"

  checkFail checkcompatibilityTest

  ./cli.sh cc \
   ${projectDir}/cli/src/test/resources/oas/compatibility-left.yaml \
   ${projectDir}/cli/src/test/resources/oas/compatibility-right.yaml 2>&1 \
   | grep "adding is not allowed on right side"

  checkFail checkcompatibilityTest
  echo "./cli.sh checkcompatibility test success"
}

helpTest
codegenerateTest
docgenerateTest
checkstyleTest
checkcompatibilityTest
