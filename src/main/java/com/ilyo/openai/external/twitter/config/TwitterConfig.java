package com.ilyo.openai.external.twitter.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "twitter")
public record TwitterConfig(String apiRoot, String clientToken, String clientKey, String clientSecret,
                            String callbackUrl,
                            List<String> influencersList, String tweetsMaxResults
) {

}
