package com.ilyo.openai.external.twitter.service;

import java.time.Instant;
import java.util.List;

import com.ilyo.openai.external.twitter.dto.TweetUser;
import com.ilyo.openai.external.twitter.dto.response.LatestTweetsResponse;
import com.ilyo.openai.external.twitter.dto.response.TweetCreationResponse;
import com.ilyo.openai.external.twitter.dto.response.TweetLikeResponse;
import lombok.SneakyThrows;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The interface Twitter service.
 */
public interface TwitterService {

  /**
   * Gets filtered tweets.
   *
   * @return the filtered tweets
   */
  LatestTweetsResponse getLatestTweets(final Instant startTime, String searchQuery, boolean withInfluencers);

  TweetCreationResponse publishTweet(final String tweetText);

  TweetCreationResponse replyToTweet(final String text, final String targetTweetId);

  TweetLikeResponse likeTweet(final String userId, final String targetTweetId);

  static String buildInfluencersQuery(final List<String> influencersList) {
    final var influencers = influencersList.stream().map(username -> "from:" + username).toArray(String[]::new);
    return String.join(" OR ", influencers);
  }

  @SneakyThrows
  default String buildOAuth2AuthorizeUrl(final String clientId, final String redirectUrl,
                                         final String scope, final String state) {
    var authorizeUrl = new StringBuilder("https://x.com/i/oauth2/authorize?");

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
