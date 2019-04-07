FROM openjdk:8-jdk-alpine

LABEL maintainer="kai.he517@hotmail.com"

EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/spring-boot-apis-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
