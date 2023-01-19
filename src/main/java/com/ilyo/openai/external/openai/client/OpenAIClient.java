package com.ilyo.openai.external.openai.client;

import com.ilyo.openai.external.openai.dto.CompletionsRequest;
import com.ilyo.openai.external.openai.dto.CompletionsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OpenAIClient {

    @POST("v1/completions")
    Call<CompletionsResponse> getCompletionsResult(@Body CompletionsRequest request);
}
