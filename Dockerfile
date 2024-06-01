FROM openjdk:23-ea-17-jdk-bullseye
WORKDIR /app
COPY /target/tech-check-1.0-SNAPSHOT.jar /app/
EXPOSE 8080 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "tech-check-1.0-SNAPSHOT.jar"]
