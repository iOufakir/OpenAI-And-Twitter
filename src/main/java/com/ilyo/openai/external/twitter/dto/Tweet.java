package com.ilyo.openai.external.twitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Tweet(String id,
                    @JsonProperty("author_id") String authorId,
                    @JsonProperty("created_at") LocalDateTime createdAt,
                    String text) {

}
