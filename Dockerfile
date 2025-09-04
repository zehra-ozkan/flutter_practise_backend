# Use the official Maven image with Java 21 to build the application
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app
COPY . .
# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Use a smaller official Java 21 runtime image to run the application
FROM eclipse-temurin:22-jre
WORKDIR /app
# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Expose the port your app runs on (Spring Boot default is 8080)
EXPOSE 8080
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]