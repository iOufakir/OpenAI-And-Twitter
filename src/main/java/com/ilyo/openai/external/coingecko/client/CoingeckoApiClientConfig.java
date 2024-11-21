package com.ilyo.openai.external.coingecko.client;

import java.time.Duration;

import com.ilyo.openai.core.utils.ObjectMapperUtils;
import com.ilyo.openai.external.coingecko.config.CoingeckoConfig;
import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@AllArgsConstructor
public class CoingeckoApiClientConfig {

  private final CoingeckoConfig coingeckoConfig;

  @Bean
  public CoingeckoApiClient coingeckoApiClient() {
    var httpClient = new OkHttpClient().newBuilder()
        .addInterceptor(getInterceptor())
        .readTimeout(Duration.ofMinutes(1))
        .build();

    return new Retrofit.Builder()
        .client(httpClient)
        .baseUrl(coingeckoConfig.apiRoot())
        .addConverterFactory(JacksonConverterFactory.create(ObjectMapperUtils.getObjectMapper()))
        .build()
        .create(CoingeckoApiClient.class);
  }

  private Interceptor getInterceptor() {
    return (Interceptor.Chain chain) -> {
      var request = chain.request().newBuilder()
          .addHeader("x-cg-demo-api-key", coingeckoConfig.apiKey())
          .build();
      return chain.proceed(request);
    };
  }
}
