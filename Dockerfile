FROM eclipse-temurin:17-jre
WORKDIR /opt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./crypto-backend.jar
ENTRYPOINT ["java","-jar","crypto-backend.jar"]

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
