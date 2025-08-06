#Use lightweight jdk-17
FROM openjdk:17-jdk-slim

#Set working directory
WORKDIR /app

#Copy jar file
COPY /target/trackwise-0.0.1-SNAPSHOT.jar trackwise-app.jar

#Export port
EXPOSE 8080

#Run the app
ENTRYPOINT [ "java", "-jar", "trackwise-app.jar" ]
