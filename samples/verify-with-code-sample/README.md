## Prerequisites
You will need:
1. [JDK 1.8][jdk]
2. [Maven 3.x][maven]

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven]: https://maven.apache.org/install.html

## Configure plugin
```xml
<plugin>
    <groupId>org.apache.servicecomb.toolkit</groupId>
    <artifactId>toolkit-maven-plugin</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <configuration>
        <!-- Set to 'code' to resolve the current project. Set to 'contract' to resolve the contract file for the specified path.If not set, the default is 'code' -->
        <sourceType>code</sourceType>
        <!-- Sample contract file path, must be set -->
        <destinationContractPath>./contract</destinationContractPath>
    </configuration>
</plugin>
```

## Running Demo
```
mvn toolkit:verify
```

## Output Result
```
[INFO] Contract is not matched, difference is as follows
[INFO] ./contract/GreetingController.yaml vs /opt/sunlisen/workspace/servicecomb-toolkit/samples/verify-with-code-sample/target/tmp-contract/2244468406394280775/GreetingController.yaml
@@ -1,20 +1,3 @@
1    -## ---------------------------------------------------------------------------                     
2    -## Licensed to the Apache Software Foundation (ASF) under one or more                              
3    -## contributor license agreements.  See the NOTICE file distributed with                           
4    -## this work for additional information regarding copyright ownership.                             
5    -## The ASF licenses this file to You under the Apache License, Version 2.0                         
6    -## (the "License"); you may not use this file except in compliance with                            
7    -## the License.  You may obtain a copy of the License at                                           
8    -##                                                                                                 
9    -##      http://www.apache.org/licenses/LICENSE-2.0                                                 
10   -##                                                                                                 
11   -## Unless required by applicable law or agreed to in writing, software                             
12   -## distributed under the License is distributed on an "AS IS" BASIS,                               
13   -## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                        
14   -## See the License for the specific language governing permissions and                             
15   -## limitations under the License.                                                                  
16   -## ---------------------------------------------------------------------------                     
17   -                                                                                                   
18    ---                                                                                                1     ---
19    swagger: "2.0"                                                                                     2     swagger: "2.0"
20    info:                                                                                              3     info:

this is end of compare
```