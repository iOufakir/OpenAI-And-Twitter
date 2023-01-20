package com.ilyo.openai.external.twitter.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(@JsonProperty("token_type") String tokenType,
                                  @JsonProperty("access_token") String accessToken,
                                  @JsonProperty("refresh_token") String refreshToken) {
}
