FROM amazoncorretto:17-al2-jdk

# Install necessary dependencies (Firefox and Geckodriver)
RUN yum update -y && \
    yum install -y firefox wget tar

# Install Geckodriver
RUN wget https://github.com/mozilla/geckodriver/releases/download/v0.35.0/geckodriver-v0.35.0-linux64.tar.gz && \
    tar -xvzf geckodriver-v0.35.0-linux64.tar.gz && \
    mv geckodriver /usr/local/bin/

WORKDIR /opt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./openai-with-twitter-backend.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","openai-with-twitter-backend.jar"]
