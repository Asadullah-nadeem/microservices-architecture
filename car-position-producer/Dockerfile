FROM openjdk:17-jre-slim
LABEL authors="asadu"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-position-producer.jar

ENTRYPOINT ["java", "-jar", "car-position-producer.jar"]