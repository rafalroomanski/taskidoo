FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/taskidoo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]