FROM maven:3.8.3-openjdk-17 AS build

# Copy everything and see what's actually there
COPY . .
RUN echo "Contents of /app:" && ls -la
RUN echo "Current directory:" && pwd
RUN echo "Looking for pom.xml:" && find . -name "pom.xml" -type f
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim-buster
COPY --from=build /project-database/target/*.jar app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]