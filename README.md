# Introduction

This repository contains a sample project using OpenAI and Twitter, it's basically a Job scheduler that will be executed each X seconds to publish tweets on Twitter using OpenAI.

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
OPENAI_BEARER_TOKEN=YOUR_OPENAI_BEARER_TOKEN
```

In order to get the above credentials, you should create a **Twitter App**, and a new **API** in KuCoin.

# Build and Test

To build and run the application using Docker, the following needs to be done:
- First check the influence's list in the `application.yml` file, make sure it's correct.
- Open a terminal, and make sure you are in the parent project where the Dockerfile located.
...

## Swagger

Swagger documentation can be found on **/api/swagger** and is accessible only by authenticated users.

Swagger is enabled in these environments:

- local -  `http://localhost:8080/api/swagger`
