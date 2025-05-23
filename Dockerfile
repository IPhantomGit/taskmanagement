# Step 1: Build the JAR
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only the necessary files to speed up the build
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Run the JAR
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar /app/taskmanagement.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/taskmanagement.jar"]
