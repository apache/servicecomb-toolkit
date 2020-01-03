### Generate From Code Sample
本案例模拟一个多模块项目，包括以下两个服务：
* HelloService
* GoodbyeService

HelloService和GoodbyeService各提供一个接口，本案例将分别将其转换成对应的基于ServiceComb java-chassis的微服务项目

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
        <!-- 生成契约文件、文档的根目录，不设置则默认为运行命令所在目录下的 target 目录，生成的微服务工程在 project 目录，契约文件在 contract 目录，文档在 document 目录 -->
        <outputDirectory>./target</outputDirectory>
        <!-- 生成的微服务代码工程配置 -->
        <service>
            <!-- 微服务的类型，可生成 provider/consumer/all，默认值为 all -->
            <serviceType>all</serviceType>
            <groupId>org.apache.servicecom.toolkit</groupId>
            <artifactId>generate-from-code-sample</artifactId>
        </service>
    </configuration>
</plugin>
```

### 步骤2：执行插件
在命令行中执行如下的maven命令：
```
mvn toolkit:generate
```

### 步骤3：输出结果
转换的结果在generate-from-code-sample的target目录下，分别输出契约，文档和基于ServiceComb java-chassis的微服务项目
```
target/
├── contract
│   ├── GoodbyeService
│   │   └── GoodbyeController.yaml
│   └── HelloService
│       └── HelloController.yaml
├── document
│   ├── GoodbyeService
│   │   └── GoodbyeController.html
│   └── HelloService
│       └── HelloController.html
└── project
    ├── GoodbyeService-consumer
    │   ├── pom.xml
    │   └── src
    ├── GoodbyeService-model
    │   └── pom.xml
    ├── GoodbyeService-provider
    │   ├── pom.xml
    │   └── src
    ├── HelloService-consumer
    │   ├── pom.xml
    │   └── src
    ├── HelloService-model
    │   └── pom.xml
    ├── HelloService-provider
    │   ├── pom.xml
    │   └── src
    └── pom.xml
```