FROM openjdk:17

RUN apt-get update && apt-get install -y tzdata

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY target/MultiTaskBot-0.0.1-SNAPSHOT.jar MultiTaskBot-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/MultiTaskBot-0.0.1-SNAPSHOT.jar"]
