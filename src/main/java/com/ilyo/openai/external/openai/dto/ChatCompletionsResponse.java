package com.ilyo.openai.external.openai.dto;

import java.util.List;

public record ChatCompletionsResponse(String id, String model, List<ChatCompletionsChoices> choices) {
}
