FROM openjdk:8

CMD sleep 10000 && java -jar demo-0.0.1-SNAPSHOT.jar
COPY ./target/demo-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
