# Introduction

This repository contains the Crypto Backend API, it's basically a Job scheduler that will be executed each X seconds to get the latest tweets and start trading using KuCoin.

## Prerequisites

- Java 17
- IntelliJ
- Docker

### Running the application

In order to run the application locally, the following needs to be done:
- Install Docker and execute the `docker-compose-yml` by a command line, in order to create a postgres Database.
- Make sure JAVA_HOME is using Java 17
- `local` Spring profile needs to be set in the Run Configuration (in the `Active profiles` field)
- You will need the following environment variables :

```
TWITTER_BEARER_TOKEN=YOUR_TWITTER_APP_BEARER_TOKEN
KUCOIN_API_KEY=YOUR_KUCOIN_API_KEY;
KUCOIN_API_SECRET=YOUR_KUCOIN_API_SECRET
KUCOIN_API_PASS_PHRASE=YOUR_KUCOIN_API_PASS_PHRASE
OPENAI_BEARER_TOKEN=YOUR_OPENAI_BEARER_TOKEN
```

In order to get the above credentials, you should create a **Twitter App**, and a new **API** in KuCoin.

# Build and Test

To build and run the application using Docker, the following needs to be done:
- First check the influence's list in the `application.yml` file, make sure it's correct.
- Open a terminal, and make sure you are in the parent project where the Dockerfile located.
- Go to `/local/kucoin-java-sdk` folder and run `mvn clean install -DskipTests` 
- Go back to parent project folder and run again `mvn clean install`
- `docker build -t spring/crypto-api:latest .`
- Configure the **env variables** in the docker-compose (prod)
- Execute the docker compose for production use `docker-compose -f docker-compose-prod.yml up -d`

## Swagger

Swagger documentation can be found on **/api/swagger** and is accessible only by authenticated users.

Swagger is enabled in these environments:

- local -  `http://localhost:8080/api/swagger`

# Security
To consume the APIs of the application, it is mandatory to authenticate through the API **/api/crypto-api/v1/auth/signin** which takes as input an object in the format : 
{
    username: 'username',
    password: 'password'
}.
A token will be generated following authentication. This token will be sent with each request to identify the user.
The addition of users can be ensured by the public API **/api/crypto-api/v1/users/save** which takes as input an object of the following format:
{
    username: 'username',
    password: 'password'
}
