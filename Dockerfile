# Build stage
FROM maven:3.8.5-openjdk-11-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/financial-transfer-scheduler-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
