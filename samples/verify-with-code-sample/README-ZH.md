### Verify With Code Sample
本案例包括以下服务和一个存放标准契约的contract目录
* GreetingService  
本案例将使用标准契约校验当前项目的契约并输出校验结果到控制台

### 运行环境
1. [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Maven 3.x](https://maven.apache.org/install.html)

### 步骤1： 配置插件
在示例项目根目录的pom.xml文件中，新增toolkit-maven-plugin插件的配置，如下
```xml
<plugin>
    <groupId>org.apache.servicecomb.toolkit</groupId>
    <artifactId>toolkit-maven-plugin</artifactId>
    <version>0.3.0-SNAPSHOT</version>
    <configuration>
        <!-- 输入源。设置为 code，表示解析当前代码；设置为 contract，表示解析指定目录的契约文件。不设置则默认为 code -->
        <sourceType>code</sourceType>
        <!-- 样本契约文件目录，必须设置 -->
        <destinationContractPath>./contract</destinationContractPath>
    </configuration>
</plugin>
```

### 步骤2：执行插件
在命令行中执行如下的maven命令：
```
mvn toolkit:verify
```

### 步骤3：输出结果
校验的结果直接在控制台上输出
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