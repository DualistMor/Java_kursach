FROM openjdk:8

COPY target/services.jar services.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "services.jar"]
