FROM maven:3.8.3-openjdk-17 AS build
COPY . .
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim-buster
COPY --from=build /app/target/*.jar app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]