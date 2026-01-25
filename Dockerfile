#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -B
#
#COPY src ./src
#
#RUN mvn clean package
#
#FROM openjdk:21-jdk AS runner
#
#WORKDIR /app
#
#COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar
#
#EXPOSE 4000
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

#Uses JRE instead of JDK (you don't need compiler at runtime),much smaller ~200MB vs ~800MB
#FROM openjdk:22-jdk
FROM eclipse-temurin:21-jre-alpine AS runner
ADD target/patient-service-0.0.1-SNAPSHOT.jar patient-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar","/patient-service-0.0.1-SNAPSHOT.jar"]