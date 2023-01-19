package com.ilyo.openai.external.openai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai")
public record OpenAIConfig(String apiRoot, String clientToken, String model
) {

}
