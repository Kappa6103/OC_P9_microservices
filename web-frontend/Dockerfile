# Use existing image as base
FROM eclipse-temurin:21-jre

WORKDIR /opt/springboot

# Retrieve needed files and dependencies
COPY ./target/web-frontend-0.0.1-SNAPSHOT.jar web-frontend.jar

# Install a sweet file manager
#RUN apt-get update && apt-get install -y mc

# Specify a start-up command with the active profile, this is the "fixex arg"
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/opt/springboot/web-frontend.jar"]