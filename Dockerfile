FROM openjdk:8-jdk-alpine

WORKDIR /usr/src/salesmgmt

COPY target/salesmgmt-authorizatoin.war ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "salesmgmt-authorization.war"]