#FOR linux/amd64
FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk
WORKDIR /opt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./openai-with-twitter-backend.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","openai-with-twitter-backend.jar"]
