FROM openjdk:23-ea-17-jdk-bullseye
WORKDIR /app
COPY /target/tech-check-1.0-SNAPSHOT.jar /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tech-check-1.0-SNAPSHOT.jar"]
