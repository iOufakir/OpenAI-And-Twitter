package com.ilyo.openai.external.openai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatCompletionsRequest(String model, List<ChatMessage> messages,
                                     @JsonProperty("max_tokens") int maxTokens,
                                     float temperature) {
}
