package com.ilyo.openai.external.twitter.client;

import com.ilyo.openai.external.twitter.config.TwitterConfig;
import com.ilyo.openai.util.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@AllArgsConstructor
public class TwitterApiClientConfig {

    private final TwitterConfig twitterConfig;

    @Bean
    public TwitterApiClient twitterApiClient() {
        var httpClient = new OkHttpClient().newBuilder().build();

        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(twitterConfig.apiRoot())
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapperUtils.getObjectMapper()))
                .build()
                .create(TwitterApiClient.class);
    }

}
