# Use a lightweight OpenJDK 17 base image for Spring Boot applications
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your target directory into the container
# Replace 'Food-Delivery-App-Backend-0.0.1-SNAPSHOT.jar' with your actual JAR file name
ARG JAR_FILE=target/Food-Delivery-App-Backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java","-jar","app.jar"]

