package com.ilyo.openai.core.service;

import static com.ilyo.openai.core.utils.Constants.AMAZON_AFFILIATE_TARGET_PRODUCT_NAME;
import static com.ilyo.openai.core.utils.Constants.AMAZON_AFFILIATE_TARGET_PRODUCT_URL;
import static com.ilyo.openai.core.utils.Constants.TWITTER_AUTHENTICATED_USER_ID;
import static com.ilyo.openai.core.utils.Constants.TWITTER_SEARCH_QUERY_AFFILIATE_PRODUCT;
import static com.ilyo.openai.core.utils.Constants.TWITTER_TEXT_SEARCH_QUERY;
import static com.ilyo.openai.external.openai.enums.OpenAIRoles.ASSISTANT;
import static com.ilyo.openai.external.openai.enums.OpenAIRoles.USER;
import static com.ilyo.openai.core.utils.OpenAIUtils.OPENAI_PROMPT_IF_SHOULD_PROMOTE_PRODUCT;
import static com.ilyo.openai.core.utils.OpenAIUtils.OPENAI_PROMPT_PROMOTE_AFFILIATE_PRODUCT;
import static com.ilyo.openai.core.utils.OpenAIUtils.OPENAI_PROMPT_REPLY_TO_TWEET;
import static com.ilyo.openai.core.utils.OpenAIUtils.OPENAI_PROMPT_WRITE_TWEET;
import static com.ilyo.openai.core.utils.OpenAIUtils.OPENAI_TEMPERATURE;
import static com.ilyo.openai.core.utils.OpenAIUtils.OPENAI_TWITTER_MAX_TOKENS;
import static com.ilyo.openai.external.twitter.service.TwitterService.getUser;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.ilyo.openai.external.openai.dto.ChatMessage;
import com.ilyo.openai.external.openai.service.OpenAIService;
import com.ilyo.openai.external.twitter.service.TwitterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

  private final TwitterService twitterService;
  private final OpenAIService openAIService;

  public void launchTwitterAutoPublish() {
    var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofHours(2).toSeconds()),
        TWITTER_TEXT_SEARCH_QUERY, true);

    if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
      if (!tweets.data().isEmpty()) {
        var originalTweet = tweets.data().get(0);
        log.info("[Twitter Auto Publish] Starting to publish a new tweet from this tweet: {} - FROM: @{}", originalTweet.text(),
            getUser(tweets.includes().users(), originalTweet.authorId()));
        var generatedTweet =
            openAIService.sendPrompt(List.of(new ChatMessage(USER.name().toLowerCase(), OPENAI_PROMPT_WRITE_TWEET.formatted(originalTweet.text()))), OPENAI_TWITTER_MAX_TOKENS,
                OPENAI_TEMPERATURE);
        twitterService.publishTweet(generatedTweet);
      } else {
        log.warn("[Twitter Auto Publish] Couldn't get any tweets!");
      }
    }
  }

  public void launchTwitterAutoReplies() {
    var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofMinutes(5).toSeconds()),
        TWITTER_TEXT_SEARCH_QUERY, true);

    if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
      if (!tweets.data().isEmpty()) {
        var originalTweet = tweets.data().get(0);
        log.info("[Twitter Auto Replies] Starting to reply to the Tweet: {} - FROM: @{}", originalTweet.text(),
            getUser(tweets.includes().users(), originalTweet.authorId()));

        // Don't include the tweet that contains only an image
        if (!originalTweet.text().startsWith("http")) {
          var generatedReply = openAIService.sendPrompt(List.of(new ChatMessage(USER.name().toLowerCase(), OPENAI_PROMPT_REPLY_TO_TWEET.formatted(originalTweet.text()))),
              OPENAI_TWITTER_MAX_TOKENS, OPENAI_TEMPERATURE);
          twitterService.replyToTweet(generatedReply, originalTweet.id());
          twitterService.likeTweet(TWITTER_AUTHENTICATED_USER_ID, originalTweet.id());
        }
      } else {
        log.warn("[Twitter Auto Replies] Couldn't get any tweets!");
      }
    }
  }

  public void launchAmazonAffiliateProgram() {
    var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofMinutes(2).toSeconds()),
        TWITTER_SEARCH_QUERY_AFFILIATE_PRODUCT, false);

    if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data()) && (!tweets.data().isEmpty())) {
      var originalTweet = tweets.data().get(0);
      log.info("[Amazon Affiliate] Check Tweet: {} - FROM: @{}", originalTweet.text(),
          getUser(tweets.includes().users(), originalTweet.authorId()));

      // Check if the tweet worth it or not by asking AI
      var userPrompt = OPENAI_PROMPT_IF_SHOULD_PROMOTE_PRODUCT.formatted(AMAZON_AFFILIATE_TARGET_PRODUCT_NAME, originalTweet);
      var chatGptResponse = openAIService.sendPrompt(List.of(new ChatMessage(USER.name().toLowerCase(), userPrompt)), OPENAI_TWITTER_MAX_TOKENS, OPENAI_TEMPERATURE);

      if (chatGptResponse.toUpperCase(Locale.ROOT).contains("YES")) {
        userPrompt = OPENAI_PROMPT_PROMOTE_AFFILIATE_PRODUCT.formatted(AMAZON_AFFILIATE_TARGET_PRODUCT_NAME,
            AMAZON_AFFILIATE_TARGET_PRODUCT_URL, originalTweet);
        chatGptResponse = openAIService.sendPrompt(List.of(new ChatMessage(ASSISTANT.name().toLowerCase(), "Don't use hashtags!"),
            new ChatMessage(USER.name().toLowerCase(), userPrompt)), OPENAI_TWITTER_MAX_TOKENS, OPENAI_TEMPERATURE);
        twitterService.replyToTweet(chatGptResponse.replace("\"", ""), originalTweet.id());
        twitterService.likeTweet(TWITTER_AUTHENTICATED_USER_ID, originalTweet.id());
      }
    }
  }
}
