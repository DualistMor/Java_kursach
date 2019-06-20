FROM openjdk:8
COPY ./target/feign-service.jar .
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "feign-service.jar"]