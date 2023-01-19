package com.ilyo.openai.external.twitter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "twitter")
public record TwitterConfig(String apiRoot, String clientToken, String clientKey, String clientSecret,
                            String callbackUrl,
                            String influencersList, String tweetsMaxResults
) {

}
