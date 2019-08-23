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
        <sourceType>contract</sourceType>
        <!-- The root directory to save contract file and document. If it is not set, the default is the 'target' under the directory where the command is run -->
        <outputDirectory>./target</outputDirectory>
        <!-- Input contract file path. Valid when sourceType is set to 'contract', must be set -->
        <contractLocation>./contract</contractLocation>
        <!-- Generated microservice project configuration -->
        <service>
            <!-- Microservice type,can generated 'provider/consumer/all',the default is 'all' -->
            <serviceType>provider</serviceType>
        </service>
    </configuration>
</plugin>

```

## Running Demo
```
mvn toolkit:generate
```

## Output Result
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