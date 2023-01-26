package com.ilyo.openai.external.twitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TweetReply(@JsonProperty("in_reply_to_tweet_id") String tweetId) {

}
