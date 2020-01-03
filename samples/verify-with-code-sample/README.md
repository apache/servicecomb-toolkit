### Verify With Code Sample
This case simulates a multi-module project that includes one of the following services and a contract directory that holds standard contracts.  
This case will use the standard contract to verify the contract of the current project and output the verification result to the console

### Prerequisites
You will need:
1. [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Maven 3.x](https://maven.apache.org/install.html)

### Step1: Configure plugin
In the pom.xml file in the root directory of the current sample project, add the configuration of the toolkit-maven-plugin plugin as follows.
```xml
<plugin>
    <groupId>org.apache.servicecomb.toolkit</groupId>
    <artifactId>toolkit-maven-plugin</artifactId>
    <version>0.3.0-SNAPSHOT</version>
    <configuration>
        <!-- Set to 'code' to resolve the current project. Set to 'contract' to resolve the contract file for the specified path.If not set, the default is 'code' -->
        <sourceType>code</sourceType>
        <!-- Sample contract file path, must be set -->
        <destinationContractPath>./contract</destinationContractPath>
    </configuration>
</plugin>
```

### Step2: Running Demo
Execute the following maven command on the command line
```
mvn toolkit:verify
```

### Step3: Output Result
The result of the verification is output directly on the console
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