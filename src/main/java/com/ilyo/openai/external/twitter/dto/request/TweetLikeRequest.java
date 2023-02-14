package com.ilyo.openai.external.twitter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TweetLikeRequest(@JsonProperty("tweet_id") String tweetId) {

}
