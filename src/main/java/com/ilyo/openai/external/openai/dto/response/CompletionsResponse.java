package com.ilyo.openai.external.openai.dto.response;

import java.util.List;

import com.ilyo.openai.external.openai.dto.CompletionsChoices;

public record CompletionsResponse(String id, String model, List<CompletionsChoices> choices) {
}
