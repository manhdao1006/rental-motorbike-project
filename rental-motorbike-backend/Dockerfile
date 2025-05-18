# Stage 1: Build với Maven + JDK17
FROM maven:3-openjdk-17 AS build
WORKDIR /app

# Copy file cấu hình và source
COPY pom.xml ./
COPY src ./src

# Tải dependency offline và build package
RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

# Stage 2: Runtime với OpenJDK17
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy jar đã build từ stage trước
COPY --from=build /app/target/*.jar app.jar

# Lắng nghe port 8080 (Spring Boot mặc định)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
