## Prerequisites
You will need:
1. [JDK 1.8][jdk]
2. [Maven 3.x][maven]

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