FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml ./
COPY src ./src
COPY wallet ./wallet

RUN mvn clean package

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/target/logimascota-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build /app/wallet /app/wallet

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]