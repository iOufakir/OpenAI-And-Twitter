package com.ilyo.openai.service;

import com.ilyo.openai.external.twitter.dto.Tweet;
import com.ilyo.openai.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ilyo.openai.service.TwitterService.getUser;
import static com.ilyo.openai.util.Constants.OPENAI_PROMPT_REPLY_TO_TWEET;
import static com.ilyo.openai.util.Constants.OPENAI_PROMPT_WRITE_TWEET;

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


    private void publishTweetAndReply(final String tweet, final Tweet originalTweet){
        var creationResponse = twitterService.publishTweet(tweet).data();
        if(creationResponse != null){
            var generatedReply = openAIService.generateNewTweet(OPENAI_PROMPT_REPLY_TO_TWEET.formatted(originalTweet.text()));
            twitterService.replyToTweet(generatedReply, originalTweet.id());
        }
    }
}
