
package com.ilyo.openai.mapper;

import com.ilyo.openai.dto.TweetResponse;
import com.ilyo.openai.external.twitter.dto.Tweet;
import com.ilyo.openai.external.twitter.dto.TweetUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetsMapper {

    @Mapping(target = "id", source = "tweet.id")
    TweetResponse toTweetResponse(Tweet tweet, TweetUser tweetUser, String currencyToBuy);

}
