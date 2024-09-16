FROM maven:3.9.9-alpine AS build
WORKDIR /encurtador
COPY pom.xml .
COPY src ./src
RUN mvn clean package
FROM openjdk:21-jdk-slim
WORKDIR /encurtador
COPY --from=build /app/target/myapp.jar app.jar
EXPOSE 666