package com.ilyo.openai.service.impl;

import com.ilyo.openai.external.twitter.client.TwitterApiClient;
import com.ilyo.openai.external.twitter.config.TwitterConfig;
import com.ilyo.openai.external.twitter.dto.request.AccessTokenRequest;
import com.ilyo.openai.external.twitter.dto.request.TweetCreationRequest;
import com.ilyo.openai.external.twitter.dto.response.AccessTokenResponse;
import com.ilyo.openai.external.twitter.dto.response.LatestTweetsResponse;
import com.ilyo.openai.external.twitter.dto.response.TweetCreationResponse;
import com.ilyo.openai.service.MyWebClient;
import com.ilyo.openai.service.TwitterService;
import com.ilyo.openai.util.AuthorizationUtils;
import com.ilyo.openai.util.RetrofitUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

    private final MyWebClient myWebClient;
    private final SecureRandom secureRandom;

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

    @Override
    public TweetCreationResponse publishTweet(final String tweetText) {
        var oAuth2BearerToken = getOAuth2Token();
        var call = twitterApiClient.createTweet(oAuth2BearerToken, new TweetCreationRequest(tweetText));
        return RetrofitUtils.executeCall(call);
    }

    private String getOAuth2Token() {
        final var secretState = "state-%s".formatted(secureRandom.nextInt(9_000));
        final String authorizationUrl = buildOAuth2AuthorizeUrl(twitterConfig.clientKey(),
                twitterConfig.callbackUrl(), "users.read tweet.read tweet.write offline.access",
                secretState);
        final var code = myWebClient.extractTwitterOAuth2Code(authorizationUrl, twitterConfig.callbackUrl());
        var accessTokenResponse = getAccessToken(new AccessTokenRequest(code,
                "authorization_code", "challenge"));

        log.debug("Getting OAuth2 Access Token: {}", accessTokenResponse.accessToken());
        return AuthorizationUtils.createBearerToken(accessTokenResponse.accessToken());
    }


    private AccessTokenResponse getAccessToken(final AccessTokenRequest request) {
        var call = twitterApiClient.getAccessToken(
                AuthorizationUtils.createBasicAuthorizationHeader(twitterConfig.clientKey(), twitterConfig.clientSecret()),
                request.code(), request.grantType(), twitterConfig.clientKey(), request.codeVerifier(), twitterConfig.callbackUrl());
        return RetrofitUtils.executeCall(call);
    }
}
