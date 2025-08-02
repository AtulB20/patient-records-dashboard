# Base image (Java 17 Spring Boot)
FROM openjdk:17-jdk-slim

# Set environment
ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copy app JAR
COPY target/*.jar app.jar

# Expose port (adjust per service)
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]