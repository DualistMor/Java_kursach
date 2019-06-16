FROM openjdk:8
COPY feign-service/target/out/feign-service.jar feign-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "feign-service.jar"]