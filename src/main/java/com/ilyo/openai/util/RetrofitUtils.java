package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import retrofit2.Call;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrofitUtils {

    public static <T> T executeCall(Call<T> apiCall) {
        try {
            log.debug("REST {} request {}", apiCall.request().method(), apiCall.request().url());
            var connectionsResponse = apiCall.execute();
            return connectionsResponse.body();
        } catch (Exception ex) {
            log.error("Failed integrating with REST service", ex);
            throw new RestClientException("Failed integrating with REST service", ex);
        }
    }

}
