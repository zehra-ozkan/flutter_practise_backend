FROM maven:3.8.3-openjdk-22 AS build

WORKDIR /app
COPY . .
RUN echo "Contents of /app:" && ls -la
RUN echo "Current directory:" && pwd
RUN echo "Looking for pom.xml:" && find . -name "pom.xml" -type f
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim-buster
COPY --from=build /app/target/*.jar app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]