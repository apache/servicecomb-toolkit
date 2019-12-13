#!/bin/bash
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

# check if java install
if [ -z "$JAVA_HOME" -o ! -f "$JAVA_HOME/bin/java" ]; then
  echo Error: JAVA_HOME invalid in your environment. >&2
  echo Please set the JAVA_HOME variable in your environment to match the >&2
  echo location of your Java installation. >&2
  exit 1
fi

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

shellDir=`dirname "$PRG"`

# check if jar file exist
jarFile=$(find ${shellDir} -name "cli-*.jar" | head -n 1)

if [[ ! -f ${jarFile} ]]; then
 echo "error: cli.jar not exist. make sure it is placed in the current directory or subdirectory of this script"
 exit 1
fi

java -Dscript.name="cli.sh" -jar ${jarFile} $*
