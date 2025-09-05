FROM maven:3.8.3-openjdk-17 AS build
COPY . .
WORKDIR /project-database
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim-buster
COPY --from=build /project-database/target/*.jar app.jar
WORKDIR /project-database
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]