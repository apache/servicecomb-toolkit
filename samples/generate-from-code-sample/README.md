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
        <!-- The root directory to save contract file and document. If it is not set, the default is the 'target' under the directory where the command is run -->
        <outputDirectory>./target</outputDirectory>
        <!-- Generated microservice project configuration -->
        <service>
            <!-- Microservice type,can generated 'provider/consumer/all',the default is 'all' -->
            <serviceType>all</serviceType>
            <groupId>org.apache.servicecom.toolkit</groupId>
            <artifactId>generate-from-code-sample</artifactId>
        </service>
    </configuration>
</plugin>
```

## Running Demo
```
mvn toolkit:generate
```

### Output Result
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