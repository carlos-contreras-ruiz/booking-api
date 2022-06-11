# Booking API

The best Booking API for the very last hotel in Cancun

## How is this project made?

This project was made using java 11, maven, and Spring Boot, and could be transformed into a docker image using the
following **Dockerfile**.

    FROM openjdk:11
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]

By doing that, we could use that image in a tool like Kubernetes to avoid downtime.

This is a maven project, therefore for building the jar file we need to run the command  **mvn clean verify** and of
course, we need java and maven installed.

## What can we do with this app?

1. Check the room availability
2. Create a reservation
3. Cancel a reservation
4. Updating the reservation
5. Get the reservation and the cancellations

## How can we do all that?

#### Running the service

This service needs a PostgreSQL Database in order to work. For doing that we can use any external service we decided. In
a local environment, I do recommend using Docker and the **docker-compose.yml** file in this project. We only need to
have installed Docker and run the command **docker-compose up -d** in the folder of this project.

If we are using the local DB there is no need to change the file **application.properties** if we will use another
Postgre DB we must change the URL, username, and password of it.

## Extra documentation

In the folder **extra** of this project, there are 2 files.

1. **BookingAPI.postman_collection.json** this file is an exported collection of Postman for helping to use and
   understand the Booking API.
2. **BookingAPI-openAPI.json** this file contains the Open API specification that describes this service.

When the service is up and running, the Open API docs could be seen through the Swagger UI on the next URL
(https://host:port/swagger-ui/index.html)