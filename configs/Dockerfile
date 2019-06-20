FROM openjdk:8
COPY ./target/configs.jar .
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "configs.jar"]