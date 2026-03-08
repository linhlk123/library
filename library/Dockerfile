# ==========================================
# GIAI ĐOẠN 1: BUILD (Biên dịch code ra file .jar)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy file cấu hình thư viện và tải về trước (để cache, giúp build nhanh hơn ở các lần sau)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ source code vào và build (bỏ qua chạy Test để tiết kiệm thời gian)
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# GIAI ĐOẠN 2: RUN (Chạy ứng dụng)
# ==========================================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Lấy file .jar đã build thành công từ giai đoạn 1 sang
COPY --from=build /app/target/*.jar app.jar

# Mở cổng 8080 (cổng mặc định của Spring Boot)
EXPOSE 8080

# Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]