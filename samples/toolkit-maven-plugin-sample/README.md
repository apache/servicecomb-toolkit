# Booking Demo
This demo simulates a booking application including three services:
* booking
* car
* hotel

## Prerequisites
You will need:
1. [JDK 1.8][jdk]
2. [Maven 3.x][maven]

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven]: https://maven.apache.org/install.html

### Generate contract, document and the code based on ServiceComb
1. run the following command to generate
   ```
   mvn toolkit:generate
   ```
2. output result
   ```
    target/
    └── toolkit-maven-plugin-sample
        ├── contract
        │   ├── booking
        │   │   └── BookingController.yaml
        │   ├── car
        │   │   └── CarBookingController.yaml
        │   └── hotel
        │       └── HotelBookingController.yaml
        ├── document
        │   ├── booking
        │   │   └── BookingController.html
        │   ├── car
        │   │   ├── BookingController.html
        │   │   └── CarBookingController.html
        │   └── hotel
        │       ├── BookingController.html
        │       ├── CarBookingController.html
        │       └── HotelBookingController.html
        └── project
            ├── booking-consumer
            │   ├── pom.xml
            │   └── src
            ├── booking-model
            │   └── pom.xml
            ├── booking-provider
            │   ├── pom.xml
            │   └── src
            ├── car-consumer
            │   ├── pom.xml
            │   └── src
            ├── car-model
            │   ├── pom.xml
            │   └── src
            ├── car-provider
            │   ├── pom.xml
            │   └── src
            ├── hotel-consumer
            │   ├── pom.xml
            │   └── src
            ├── hotel-model
            │   ├── pom.xml
            │   └── src
            ├── hotel-provider
            │   ├── pom.xml
            │   └── src
            └── pom.xml

                
    ```