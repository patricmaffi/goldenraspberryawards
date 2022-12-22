FROM maven:3.8.5-openjdk-17-slim AS build

RUN apt-get update

COPY /src /app/src
COPY /pom.xml /app

RUN mvn clean package --file /app/pom.xml
#RUN mvn clean package --file pom.xml -P ${APPLICATION_ENV}
#CMD ["java", "-jar", "-Dspring.profiles.active=${APPLICATION_ENV}","/service.jar"]

FROM openjdk:17-jdk-alpine3.12
EXPOSE 8080
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]