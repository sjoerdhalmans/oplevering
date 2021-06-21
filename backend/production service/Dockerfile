FROM openjdk:8-jdk-alpine

COPY ./target/production-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch production-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","production-0.0.1-SNAPSHOT.jar"]

EXPOSE 9955