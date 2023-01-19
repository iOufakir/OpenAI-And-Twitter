package com.ilyo.openai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.ilyo.openai")
public class OpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAiApplication.class, args);
    }
}
