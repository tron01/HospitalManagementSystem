# -------- Stage 1: Build the application --------
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]



#docker build -t hospital-management-system .
#docker run -d -p 8080:8080 --name hms-container hospital-management-system