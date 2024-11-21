package com.ilyo.openai.external.coingecko.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.coingecko")
public record CoingeckoConfig(String apiRoot, String apiKey) {

}
