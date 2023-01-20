FROM eclipse-temurin:17-jre
WORKDIR /opt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./openai-with-twitter-backend.jar

ARG DRIVER_LOCAL_LOCATION=local/geckodriver
COPY ${DRIVER_LOCAL_LOCATION} ./geckodriver
RUN chmod 755 ./geckodriver

ENTRYPOINT ["java","-jar","openai-with-twitter-backend.jar"]

# ------- Another version with maven -------

#FROM maven:3.8.6 as maven
#
#ARG APP_FOLDER=/usr/src/app
#COPY . ${APP_FOLDER}
#WORKDIR ${APP_FOLDER}
#RUN mvn -f ./local/kucoin-java-sdk clean install -DskipTests
#RUN mvn clean package
#
#FROM eclipse-temurin:17-jre
#ARG JAR_FILE=crypto-api-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/crypto-webapi
#COPY --from=maven /usr/src/app/target/${JAR_FILE} .
#ENTRYPOINT ["java","-jar","crypto-api-0.0.1-SNAPSHOT.jar"]
