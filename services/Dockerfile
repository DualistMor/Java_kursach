FROM openjdk:8

COPY services/target/services.jar services.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "services.jar"]
