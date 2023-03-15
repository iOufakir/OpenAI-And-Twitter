package com.ilyo.openai.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import static com.ilyo.openai.service.TwitterService.getUser;
import static com.ilyo.openai.util.Constants.*;

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
                var generatedTweet = openAIService.generateNewTweet(OPENAI_PROMPT_WRITE_TWEET.formatted(originalTweet.text()));
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
                    var generatedReply = openAIService.generateNewTweet(OPENAI_PROMPT_REPLY_TO_TWEET.formatted(originalTweet.text()));
                    twitterService.replyToTweet(generatedReply, originalTweet.id());
                    twitterService.likeTweet(TWITTER_AUTHENTICATED_USER_ID, originalTweet.id());
                }
            } else {
                log.warn("[Twitter Auto Replies] Couldn't get any tweets!");
            }
        }
    }


    public void launchAmazonAffiliateProgram() {
        var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofMinutes(1).toSeconds()),
                TWITTER_SEARCH_QUERY_AFFILIATE_PRODUCT, false);

        if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
            if (!tweets.data().isEmpty()) {
                var originalTweet = tweets.data().get(0);
                log.info("[Amazon Affiliate] Starting to reply to the Tweet: {} - FROM: @{}", originalTweet.text(),
                        getUser(tweets.includes().users(), originalTweet.authorId()));

                var generatedReply = openAIService.generateChatMessage(
                        OPENAI_PROMPT_PROMOTE_AFFILIATE_PRODUCT.formatted(AMAZON_AFFILIATE_TARGET_PRODUCT_NAME,
                                AMAZON_AFFILIATE_TARGET_PRODUCT_URL, originalTweet));
                twitterService.replyToTweet(generatedReply, originalTweet.id());
                twitterService.likeTweet(TWITTER_AUTHENTICATED_USER_ID, originalTweet.id());
            } else {
                log.warn("[Amazon Affiliate] Couldn't get any tweets!");
            }
        }
    }
}
