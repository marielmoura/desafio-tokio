#TODO The image contains 22 critical and 58 high vulnerabilities
FROM maven:3.8.5-openjdk-11 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#TODO The image contains 20 critical and 55 high vulnerabilities
FROM openjdk:11-jdk
WORKDIR /app
COPY --from=builder /app/target/financial-transfer-scheduler-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
