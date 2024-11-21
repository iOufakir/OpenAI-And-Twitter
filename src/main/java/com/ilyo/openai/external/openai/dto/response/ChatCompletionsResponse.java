package com.ilyo.openai.external.openai.dto.response;

import java.util.List;

import com.ilyo.openai.external.openai.dto.ChatCompletionsChoices;

public record ChatCompletionsResponse(String id, String model, List<ChatCompletionsChoices> choices) {
}
