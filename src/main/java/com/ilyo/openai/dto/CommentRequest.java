package com.ilyo.openai.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(@NotBlank String text) {
}
