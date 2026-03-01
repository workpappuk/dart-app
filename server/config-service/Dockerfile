# Dockerfile for config-service (Spring Boot Java)

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar into the image
COPY build/libs/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
