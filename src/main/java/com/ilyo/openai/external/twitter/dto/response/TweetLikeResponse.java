package com.ilyo.openai.external.twitter.dto.response;


import com.ilyo.openai.external.twitter.dto.TweetCreationData;
import com.ilyo.openai.external.twitter.dto.TweetLikedData;

public record TweetLikeResponse(TweetLikedData data) {
}
