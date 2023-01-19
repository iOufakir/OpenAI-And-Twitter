package com.ilyo.openai.external.twitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TweetMeta(@JsonProperty("result_count") int resultCount){

}
