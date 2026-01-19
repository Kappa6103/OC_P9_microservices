# Use existing image as base
FROM alpine/java:22-jre

WORKDIR /opt/myApp

# Retrieve needed files and dependencies
COPY ./target/web-frontend-0.0.1-SNAPSHOT.jar web-frontend-0.0.1-SNAPSHOT.jar

# Specify a start-up command
#CMD ["java", "-jar", "/web-frontend-0.0.1-SNAPSHOT.jar"]

# Specify a start-up command with the active profile
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/web-frontend-0.0.1-SNAPSHOT.jar"]