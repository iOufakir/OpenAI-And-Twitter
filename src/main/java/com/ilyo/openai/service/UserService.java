package com.ilyo.openai.service;

import com.ilyo.openai.config.AppConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.ilyo.openai.util.Constants.OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final OpenAIService openAIService;

    private final SentimentService sentimentService;

    private final AppConfig appConfig;


    @Async
    public CompletableFuture<Boolean> isTextHarmful(final String text) {
        if (appConfig.withOpenai()) {
            return CompletableFuture.completedFuture(openAIService.isTextNegativeOrHarmful(OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL
                    .formatted(text)));
        } else {
            return CompletableFuture.completedFuture(sentimentService.isTextNegative(text));
        }
    }


}
