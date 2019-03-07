FROM openjdk:8-jdk-alpine

LABEL maintainer="kai.he517@hotmail.com"

EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/spring-boot-apis-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} spring-boot-apis.jar

# Run the jar file 
ENTRYPOINT ["java", "-jar", "/spring-boot-apis.jar"]