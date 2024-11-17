package com.ilyo.openai.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.web.client.RestClientException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrofitUtils {

    public static <T> T executeCall(Call<T> apiCall) {
        try {
            log.debug("REST {} request {}", apiCall.request().method(), apiCall.request().url());
            var connectionsResponse = apiCall.execute();
            checkResponseForErrors(apiCall.request(), connectionsResponse);
            return connectionsResponse.body();
        } catch (Exception ex) {
            log.error("Failed integrating with REST service", ex);
            throw new RestClientException("Failed integrating with REST service", ex);
        }
    }

    private static <T> void checkResponseForErrors(Request request, Response<T> response) {
        if (!response.isSuccessful()) {
            try (var errorBody = response.errorBody()) {
                var errorDetails = Objects.nonNull(errorBody) ? errorBody.string() : null;
                log.error("Error received from {}. Request body: {}\n Response code: {}, response body: {}",
                        request.url(), request.body(), response.code(), errorDetails);
            } catch (IOException e) {
                throw new RestClientException("Error received from remote call. HTTP code: %s".formatted(response.code()));
            }
        }
    }
}
