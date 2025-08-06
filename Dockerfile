# Use Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS build

#Set working directory
WORKDIR /app

# Copy source code
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the app
FROM openjdk:17-jdk-slim

#Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/trackwise-0.0.1-SNAPSHOT.jar trackwise-app.jar

#Export port
EXPOSE 8080

#Run the app
ENTRYPOINT ["java", "-jar", "trackwise-app.jar"]
