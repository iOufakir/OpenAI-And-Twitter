package com.ilyo.openai.external.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChatCompletionsRequest(String model, List<ChatMessage> messages, @JsonProperty("max_tokens") int maxTokens,
                                     float temperature) {
}
