package com.ilyo.openai.external.openai.client;

import com.ilyo.openai.external.openai.dto.request.ChatCompletionsRequest;
import com.ilyo.openai.external.openai.dto.response.ChatCompletionsResponse;
import com.ilyo.openai.external.openai.dto.request.CompletionsRequest;
import com.ilyo.openai.external.openai.dto.response.CompletionsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OpenAIClient {

    @POST("v1/completions")
    Call<CompletionsResponse> getCompletionsResult(@Body CompletionsRequest request);

    @POST("v1/chat/completions")
    Call<ChatCompletionsResponse> getChatCompletionsResult(@Body ChatCompletionsRequest request);
}
