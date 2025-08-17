ENTRYPOINT ["java","-jar","/app.jar"]
FROM ubuntu:latest
LABEL authors="asadu"

ENTRYPOINT ["top", "-b"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-position-producer.jar

ENTRYPOINT ["java", "-jar", "car-position-producer.jar"]