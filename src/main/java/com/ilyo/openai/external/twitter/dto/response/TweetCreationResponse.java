package com.ilyo.openai.external.twitter.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilyo.openai.external.twitter.dto.TweetCreationData;

public record TweetCreationResponse(TweetCreationData data) {
}
