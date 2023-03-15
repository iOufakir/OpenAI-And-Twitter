package com.ilyo.openai.service;

import com.ilyo.openai.external.openai.client.OpenAIClient;
import com.ilyo.openai.external.openai.config.OpenAIConfig;
import com.ilyo.openai.external.openai.dto.ChatCompletionsRequest;
import com.ilyo.openai.external.openai.dto.ChatMessage;
import com.ilyo.openai.external.openai.dto.CompletionsRequest;
import com.ilyo.openai.util.RetrofitUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class OpenAIService {
    private static final int OPENAI_MAX_TOKENS = 64;
    private static final float OPENAI_TEMPERATURE = 0.9f;

    private final OpenAIClient openAIClient;
    private final OpenAIConfig openAIConfig;

    public String generateNewTweet(final String prompt) {
        var request = new CompletionsRequest(openAIConfig.oldModel(),
                prompt,
                OPENAI_MAX_TOKENS,
                OPENAI_TEMPERATURE);
        var response = RetrofitUtils.executeCall(openAIClient.getCompletionsResult(request));

        if (Objects.nonNull(response) && !response.choices().isEmpty()) {
            var text = response.choices().get(0).text().strip();
            log.info("[OpenAI] Text Response: {}", text);
            return text;
        }
        return "";
    }

    // FOR the latest API
    public String generateChatMessage(final List<ChatMessage> chatMessages) {
        var request = new ChatCompletionsRequest(openAIConfig.model(),
                chatMessages,
                OPENAI_MAX_TOKENS,
                OPENAI_TEMPERATURE);
        var response = RetrofitUtils.executeCall(openAIClient.getChatCompletionsResult(request));

        if (Objects.nonNull(response) && !response.choices().isEmpty()) {
            var text = response.choices().get(0).message().content().strip();
            log.info("[OpenAI] Text Response: {}", text);
            return text;
        }
        return "";
    }

}
