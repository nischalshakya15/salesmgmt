FROM openjdk:8-jdk-alpine

WORKDIR /usr/src/salesmgmt

COPY target/salesmgmt-authorization.war ./

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "salesmgmt-authorization.war"]