FROM eclipse-temurin:17-jre
WORKDIR /opt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./openai-with-twitter-backend.jar
ENTRYPOINT ["java","-jar","openai-with-twitter-backend.jar"]
