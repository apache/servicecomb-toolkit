### Generate From Contract Sample
This case simulates an empty project, and the contracts are placed in the contract directory of the project root directory.  
This case will generate a ServiceComb java-chassis based microservice project based on the contracts in the contract directory.

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

### Step2: Running Demo
Execute the following maven command on the command line
```
mvn toolkit:generate
```

### Step3: Output Result
The generated content is in the target directory of generate-from-code-sample, including the document and the ServiceComb java-chassis-based microservice project.
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