package com.ilyo.openai.external.twitter.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilyo.openai.external.twitter.dto.Tweet;
import com.ilyo.openai.external.twitter.dto.TweetIncludes;
import com.ilyo.openai.external.twitter.dto.TweetMeta;

import java.util.List;

public record AccessTokenResponse(@JsonProperty("token_type") String tokenType,
                                  @JsonProperty("access_token") String accessToken,
                                  @JsonProperty("refresh_token") String refreshToken) {
}
