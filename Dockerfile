FROM openjdk:17

ENV TZ=Europe/Moscow

COPY target/MultiTaskBot-0.0.1-SNAPSHOT.jar MultiTaskBot-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/MultiTaskBot-0.0.1-SNAPSHOT.jar"]