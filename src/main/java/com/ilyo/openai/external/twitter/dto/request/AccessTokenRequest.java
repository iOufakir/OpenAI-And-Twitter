package com.ilyo.openai.external.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenRequest(String code,
                                 @JsonProperty("grant_type") String grantType,
                                 @JsonProperty("code_verifier") String codeVerifier) {

}
