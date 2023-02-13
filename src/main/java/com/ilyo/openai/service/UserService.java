package com.ilyo.openai.service;

import com.ilyo.openai.external.twitter.dto.Tweet;
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

    public void launchAutoTweet() {
        var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofHours(2).toSeconds()));

        if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
            if (!tweets.data().isEmpty()) {
                var originalTweet = tweets.data().get(0);
                log.info("[OpenAI] Request to generate a new tweet based on: {} - FROM: @{}", originalTweet.text(),
                        getUser(tweets.includes().users(), originalTweet.authorId()));
                var generatedTweet = openAIService.generateNewTweet(OPENAI_PROMPT_WRITE_TWEET.formatted(originalTweet.text()));
                publishTweetAndReply(generatedTweet, originalTweet);
            } else {
                log.warn("Couldn't get any tweets!");
            }
        }
    }

    public void launchAutoReplies() {
        var tweets = twitterService.getLatestTweets(Instant.now().minusSeconds(Duration.ofMinutes(10).toSeconds()));

        if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
            if (!tweets.data().isEmpty()) {

                //TODO: Reply only to one tweet using OpenAI AP
                tweets.data().forEach(tweet -> {
                    System.out.println(tweet);
                });

            } else {
                log.warn("Couldn't get any tweets!");
            }
        }
    }

    public boolean isHarmfulText(final String text) {
        return openAIService.isTextNegativeOrHarmful(OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL.formatted(text, OPENAI_PROMPT_PREFIX));
    }


    private void publishTweetAndReply(final String tweet, final Tweet originalTweet) {
        var creationResponse = twitterService.publishTweet(tweet).data();
        if (creationResponse != null) {
            var generatedReply = openAIService.generateNewTweet(OPENAI_PROMPT_REPLY_TO_TWEET.formatted(originalTweet.text()));
            twitterService.replyToTweet(generatedReply, originalTweet.id());
        }
    }
}
