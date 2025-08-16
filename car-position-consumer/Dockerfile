FROM openjdk:17-jre-slim
LABEL authors="asadu"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-position-consumer.jar

ENTRYPOINT ["java", "-jar", "car-position-consumer.jar"]