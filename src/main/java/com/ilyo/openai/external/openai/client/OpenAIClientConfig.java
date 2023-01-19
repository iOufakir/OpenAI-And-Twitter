package com.ilyo.openai.external.openai.client;

import com.ilyo.openai.external.openai.config.OpenAIConfig;
import com.ilyo.openai.util.AuthorizationUtils;
import com.ilyo.openai.util.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@AllArgsConstructor
public class OpenAIClientConfig {

    private final OpenAIConfig openAIConfig;

    @Bean
    public OpenAIClient openAIClient() {
        var httpClient = new OkHttpClient().newBuilder()
                .addInterceptor(getInterceptor())
                .build();

        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(openAIConfig.apiRoot())
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapperUtils.getObjectMapper()))
                .build()
                .create(OpenAIClient.class);
    }

    private Interceptor getInterceptor() {
        return (Interceptor.Chain chain) -> {
            var request = chain.request().newBuilder()
                    .addHeader("Authorization", AuthorizationUtils.createBearerToken(openAIConfig.clientToken()))
                    .build();

            return chain.proceed(request);
        };
    }
}
