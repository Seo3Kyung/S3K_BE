FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/backend-0.0.1-SNAPSHOT.jar s3k.jar
ENTRYPOINT ["java", "-jar", "s3k.jar"]