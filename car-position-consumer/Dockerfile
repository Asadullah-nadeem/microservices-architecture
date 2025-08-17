FROM eclipse-temurin:17-jre
FROM ubuntu:latest
LABEL authors="asadu"

ENTRYPOINT ["top", "-b"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-position-consumer.jar

ENTRYPOINT ["java", "-jar", "car-position-consumer.jar"]