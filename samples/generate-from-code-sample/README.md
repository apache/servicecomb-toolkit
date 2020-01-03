### Generate From Code Sample
This case simulates a multi-module project that includes the following two services：
* HelloService
* GoodbyeService

HelloService and GoodbyeService each provide an API interface. This case will convert them into corresponding microservices based on ServiceComb java-chassis.

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

### Step2: Running Demo
Execute the following maven command on the command line
```
mvn toolkit:generate
```

### Step3: Output Result
The result of the conversion is in the target directory of generate-from-code-sample, which respectively outputs the contract, the document and the microservice project based on ServiceComb java-chassis.
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