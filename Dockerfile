FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY . .
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim-buster
COPY --from=builder /app/target/*.jar app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]