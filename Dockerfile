FROM amazoncorretto:17-al2-jdk
WORKDIR /opt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./openai-with-twitter-backend.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","openai-with-twitter-backend.jar"]
