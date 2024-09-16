FROM maven:3.9.9 AS build
WORKDIR /encurtador
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /encurtador
COPY --from=build /encurtador/target/EncurtadorUrl-1.jar app.jar
EXPOSE 666
ENTRYPOINT ["java", "-jar", "app.jar"]
