package com.ilyo.openai.service;

import com.ilyo.openai.external.openai.client.OpenAIClient;
import com.ilyo.openai.external.openai.config.OpenAIConfig;
import com.ilyo.openai.external.openai.dto.CompletionsRequest;
import com.ilyo.openai.util.RetrofitUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class OpenAIService {

    private static final String OPENAI_PROMPT_TEXT = "Write a tweet as a real Human based for this tweet: %s";
    private static final int OPENAI_MAX_TOKENS = 64;
    private static final float OPENAI_TEMPERATURE = 0.9f;

    private final OpenAIClient openAIClient;
    private final OpenAIConfig openAIConfig;

    public String generateNewTweet(final String text) {
        var request = new CompletionsRequest(openAIConfig.model(),
                OPENAI_PROMPT_TEXT.formatted(text),
                OPENAI_MAX_TOKENS,
                OPENAI_TEMPERATURE);
        var response = RetrofitUtils.executeCall(openAIClient.getCompletionsResult(request));

        if (Objects.nonNull(response) && !response.choices().isEmpty()) {
            return response.choices().get(0).text().strip();
        }
        return "";
    }
}
