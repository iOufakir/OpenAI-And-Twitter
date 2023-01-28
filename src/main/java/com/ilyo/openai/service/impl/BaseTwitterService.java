package com.ilyo.openai.service.impl;

import com.ilyo.openai.external.twitter.client.TwitterApiClient;
import com.ilyo.openai.external.twitter.config.TwitterConfig;
import com.ilyo.openai.external.twitter.dto.response.LatestTweetsResponse;
import com.ilyo.openai.service.TwitterService;
import com.ilyo.openai.util.AuthorizationUtils;
import com.ilyo.openai.util.RetrofitUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

import static com.ilyo.openai.util.Constants.TWITTER_SEARCH_QUERY;
import static com.ilyo.openai.util.Constants.TWITTER_TEXT_SEARCH_QUERY;

@Service
@AllArgsConstructor
@Slf4j
public class BaseTwitterService implements TwitterService {

    private final TwitterApiClient twitterApiClient;
    private final TwitterConfig twitterConfig;

    @Override
    public LatestTweetsResponse getLatestTweets() {
        var startTime = Instant.now().minusSeconds(LATEST_TWEETS_SECONDS_TO_SUBTRACT)
                .atZone(ZoneOffset.UTC) // Twitter use UTC
                .toInstant();

        log.info("Get Latest Tweets using {}", startTime);

        var call = twitterApiClient
                .getPublicLatestTweets(AuthorizationUtils.createBearerToken(twitterConfig.clientToken()), TWITTER_SEARCH_QUERY.formatted(TWITTER_TEXT_SEARCH_QUERY,
                                getInfluencersQuery(twitterConfig.influencersList().split(","))),
                        twitterConfig.tweetsMaxResults(),
                        startTime);
        return RetrofitUtils.executeCall(call);
    }

}
