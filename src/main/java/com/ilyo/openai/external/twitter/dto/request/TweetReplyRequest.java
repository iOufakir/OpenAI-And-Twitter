package com.ilyo.openai.external.twitter.dto.request;

import com.ilyo.openai.external.twitter.dto.TweetReply;

public record TweetReplyRequest(String text, TweetReply reply) {

}
