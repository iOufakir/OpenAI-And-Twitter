package com.ilyo.openai.external.openai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompletionsRequest(String model, String prompt, @JsonProperty("max_tokens") int maxTokens,
                                 float temperature) {
}
