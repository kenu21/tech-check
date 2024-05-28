FROM openjdk:17
WORKDIR /app
COPY /target/tech-check-1.0-SNAPSHOT.jar /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tech-check-1.0-SNAPSHOT.jar"]
