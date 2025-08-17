FROM eclipse-temurin:17-jre-alpine
LABEL authors="asadu"

ENTRYPOINT ["top", "-b"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://docker-mongodb:27017/car", "-jar", "app.jar"]