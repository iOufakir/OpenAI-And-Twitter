package com.ilyo.openai.external.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TweetCreationRequest(String text) {

}
