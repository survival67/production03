FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /application
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /application
COPY --from=build /application/target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]