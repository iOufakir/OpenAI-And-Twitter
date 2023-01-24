package com.ilyo.openai.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final TwitterService twitterService;

    private final OpenAIService openAIService;

    public void launchAutoTweet() {
        var tweets = twitterService.getLatestTweets();

        if (Objects.nonNull(tweets) && Objects.nonNull(tweets.data())) {
            if (!tweets.data().isEmpty()) {
                var targetTweet = tweets.data().get(0);
                log.info("[OpenAI] Request to generate a new tweet based on: {} - FROM: @{}", targetTweet.text(),
                        TwitterService.getUser(tweets.includes().users(), targetTweet.authorId()));
                var newTweet = openAIService.generateNewTweet(targetTweet.text());
                var creationResponse = twitterService.publishTweet(newTweet);
                log.info("[OpenAI] Response: {}", creationResponse.data().text());
            } else {
                log.info("Couldn't get any tweets!");
            }
        }
    }
}
