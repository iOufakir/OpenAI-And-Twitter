package com.ilyo.openai.external.twitter.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Tweet(String id,
                    @JsonProperty("author_id") String authorId,
                    @JsonProperty("created_at") LocalDateTime createdAt,
                    String text) {

}
