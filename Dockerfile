FROM openjdk:17

COPY target/MultiTaskBot-0.0.1-SNAPSHOT.jar MultiTaskBot-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/MultiTaskBot-0.0.1-SNAPSHOT.jar"]