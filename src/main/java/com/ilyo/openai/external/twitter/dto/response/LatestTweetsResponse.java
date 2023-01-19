package com.ilyo.openai.external.twitter.dto.response;


import com.ilyo.openai.external.twitter.dto.Tweet;
import com.ilyo.openai.external.twitter.dto.TweetIncludes;
import com.ilyo.openai.external.twitter.dto.TweetMeta;

import java.util.List;

public record LatestTweetsResponse(List<Tweet> data, TweetIncludes includes, TweetMeta meta) {
}
