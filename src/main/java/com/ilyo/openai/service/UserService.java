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
        var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofHours(2).toSeconds()));

        if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
            if (!tweets.data().isEmpty()) {
                var originalTweet = tweets.data().get(0);
                log.info("[Twitter] Starting to publish a new tweet from this tweet: {} - FROM: @{}", originalTweet.text(),
                        getUser(tweets.includes().users(), originalTweet.authorId()));
                var generatedTweet = openAIService.generateNewTweet(OPENAI_PROMPT_WRITE_TWEET.formatted(originalTweet.text()));
                twitterService.publishTweet(generatedTweet);
            } else {
                log.warn("Couldn't get any tweets!");
            }
        }
    }

    public void launchTwitterAutoReplies() {
        var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofMinutes(5).toSeconds()));

        if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
            if (!tweets.data().isEmpty()) {
                var originalTweet = tweets.data().get(0);
                log.info("[Twitter] Starting to reply to the Tweet: {} - FROM: @{}", originalTweet.text(),
                        getUser(tweets.includes().users(), originalTweet.authorId()));

                // Don't include the tweet that contains only an image
                if (!originalTweet.text().startsWith("http")) {
                    var generatedReply = openAIService.generateNewTweet(OPENAI_PROMPT_REPLY_TO_TWEET.formatted(originalTweet.text()));
                    twitterService.replyToTweet(generatedReply, originalTweet.id());
                    twitterService.likeTweet(TWITTER_AUTHENTICATED_USER_ID, originalTweet.id());
                }
            } else {
                log.warn("Couldn't get any tweets!");
            }
        }
    }
}
