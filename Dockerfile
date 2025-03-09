FROM openjdk:21-jdk-slim

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#RUN mkdir -p /usr/share/fonts/
#COPY fonts/ /usr/share/fonts

WORKDIR /app

COPY target/info-collect-tool-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]