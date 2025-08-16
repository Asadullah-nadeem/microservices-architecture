FROM openjdk:11-jre
LABEL authors="asadu"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-webServices.jar

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://docker-mongodb:27017/car", "-jar", "car-webServices.jar"]