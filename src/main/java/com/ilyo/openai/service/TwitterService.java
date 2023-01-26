package com.ilyo.openai.service;

import com.ilyo.openai.external.twitter.dto.TweetUser;
import com.ilyo.openai.external.twitter.dto.response.LatestTweetsResponse;
import com.ilyo.openai.external.twitter.dto.response.TweetCreationResponse;
import lombok.SneakyThrows;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * The interface Twitter service.
 */
public interface TwitterService {
    /**
     * The constant LATEST_TWEETS_SECONDS_TO_SUBTRACT.
     */
    int LATEST_TWEETS_SECONDS_TO_SUBTRACT = 2 * 3600;

    /**
     * Gets filtered tweets.
     *
     * @return the filtered tweets
     */
    LatestTweetsResponse getLatestTweets();

    TweetCreationResponse publishTweet(final String tweetText);

    TweetCreationResponse replyToTweet(final String text, final String targetTweetId);

    @SneakyThrows
    default String buildOAuth2AuthorizeUrl(final String clientId, final String redirectUrl,
                                           final String scope, final String state) {
        var authorizeUrl = new StringBuilder("https://twitter.com/i/oauth2/authorize?");

        authorizeUrl.append("code_challenge=%s".formatted("challenge"))
                .append("&code_challenge_method=%s".formatted("plain"))
                .append("&response_type=%s".formatted("code"))
                .append("&redirect_uri=%s".formatted(redirectUrl))
                .append("&client_id=%s".formatted(clientId))
                .append("&scope=%s".formatted(scope))
                .append("&state=%s".formatted(state));

        return UriComponentsBuilder.fromUriString(authorizeUrl.toString()).build().encode().toString();
    }

    /**
     * Gets influencers query.
     *
     * @param list the list
     * @return the influencers query
     */
    default String getInfluencersQuery(final String[] list) {
        var influencesQuery = new StringBuilder();
        for (var i = 0; i < list.length; i++) {
            if (i < list.length - 1) {
                influencesQuery.append("from:%s OR ".formatted(list[i]));
            } else {
                influencesQuery.append("from:%s".formatted(list[i]));
            }
        }
        return influencesQuery.toString();
    }


    /**
     * Gets user.
     *
     * @param users  the users
     * @param userId the user id
     * @return the user
     */
    static TweetUser getUser(final List<TweetUser> users, final String userId) {
        return users.stream()
                .filter(user -> userId.equals(user.id()))
                .findFirst().orElseThrow();
    }


}
