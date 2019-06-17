FROM openjdk:8
COPY target/service-registry.jar service-registry.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "service-registry.jar"]