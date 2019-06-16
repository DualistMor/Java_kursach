FROM openjdk:8
COPY configs/target/configs.jar configs.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "configs.jar"]