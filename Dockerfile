# FROM openjdk:8-jre-alpine

# COPY ./target/Webapp-0.0.1-SNAPSHOT.jar /my-app.jar

# CMD java -jar /my-app.jar

FROM maven:3.6.3-jdk-8 

COPY ./ ./ 

RUN mvn clean package spring-boot:repackage -DskipTests

CMD ["java", "-jar", "target/Webapp-0.0.1-SNAPSHOT.jar"]

