# Use existing image as base
FROM eclipse-temurin:21-jre

WORKDIR /opt/springboot

# Retrieve needed files and dependencies
COPY ./target/api-gateway-webmvc-0.0.1-SNAPSHOT.jar gateway.jar

# Install a sweet file manager
RUN apt-get update && apt-get install -y mc

# Specify a start-up command, can be overriden
#CMD ["java", "-jar", "/opt/springboot/risk-assessment.jar"]

# Specify a start-up command with the active profile, this is the "fixex arg"
ENTRYPOINT ["java", "-jar", "/opt/springboot/gateway.jar"]
#CMD ["-Dspring.profiles.active=docker"]