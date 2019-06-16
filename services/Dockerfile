FROM openjdk:8
COPY services/target/out/services.jar services.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "services.jar"]