FROM java:8
COPY ./target/service-registry.jar .
HEALTHCHECK --interval=5s --timeout=3s CMD curl --fail http://config-server:8088/actuator/info || exit 1
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "service-registry.jar"]
