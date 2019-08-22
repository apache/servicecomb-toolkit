## Prerequisites
You will need:
1. [JDK 1.8][jdk]
2. [Maven 3.x][maven]

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