package com.ilyo.openai.external.openai.dto;

import java.util.List;

public record CompletionsResponse(String id, String model, List<CompletionsChoices> choices) {
}
