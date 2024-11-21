package com.ilyo.openai.external.openai.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilyo.openai.external.openai.dto.ChatMessage;

public record ChatCompletionsRequest(String model, List<ChatMessage> messages,
                                     @JsonProperty("max_tokens") int maxTokens,
                                     float temperature) {
}
