FROM openjdk:23-jdk-slim

COPY build/ /app/build/.

CMD ["tail", "-f", "/dev/null"]

ENTRYPOINT  ["java", "-jar", "/app/build/libs/workers-0.0.1-SNAPSHOT.jar"]