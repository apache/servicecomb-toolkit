### Generate From Contract Sample
本案例模拟一个空项目，在该项目根目录的contract目录放置了契约  
本案例将根据contract目录的契约生成对应的基于ServiceComb java-chassis的微服务项目

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
        <sourceType>contract</sourceType>
        <!-- 被解析的契约文件路径，在 sourceType 设置为 contract 时有效，且必须设置 -->
        <contractLocation>./contract</contractLocation>
        <!-- 生成契约文件、文档的根目录，不设置则默认为运行命令所在目录下的 target 目录，生成的微服务工程在 project 目录，契约文件在 contract 目录，文档在 document 目录 -->
        <outputDirectory>./target</outputDirectory>
        <!-- 生成的微服务代码工程配置 -->
        <service>
            <!-- 微服务的类型，可生成 provider/consumer/all，默认值为 all -->
            <serviceType>provider</serviceType>
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
生成的内容在generate-from-code-sample的target目录下，分别输出文档和基于ServiceComb java-chassis的微服务项目
```
target/
├── document
│   ├── GoodByeService
│   │   └── GoodbyeController.html
│   └── HelloService
│       └── HelloController.html
└── project
    ├── GoodByeService-model
    │   └── pom.xml
    ├── GoodByeService-provider
    │   ├── pom.xml
    │   └── src
    ├── HelloService-model
    │   └── pom.xml
    ├── HelloService-provider
    │   ├── pom.xml
    │   └── src
    └── pom.xml
```