FROM openjdk:14-jdk-alpine
EXPOSE 8084
ARG JAR_FILE=target/staff-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]