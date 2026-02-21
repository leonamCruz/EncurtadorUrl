FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
USER nobody
EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=90.0", "-jar", "app.jar"]