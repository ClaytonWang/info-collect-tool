FROM openjdk:21-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /app/logs/

RUN mkdir -p /app/uploads/

RUN mkdir -p /app/db/

RUN mkdir -p /app/zip_folder

WORKDIR /app

COPY --from=builder /app/target/info-collect-tool-0.0.1-SNAPSHOT.jar  app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]