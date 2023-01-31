package com.ilyo.openai.service;

import com.ilyo.openai.external.openai.client.OpenAIClient;
import com.ilyo.openai.external.openai.config.OpenAIConfig;
import com.ilyo.openai.external.openai.dto.CompletionsRequest;
import com.ilyo.openai.util.RetrofitUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class OpenAIService {

    private final OpenAIClient openAIClient;
    private final OpenAIConfig openAIConfig;


    public boolean isTextNegativeOrHarmful(final String prompt) {
        var request = new CompletionsRequest(openAIConfig.model(),
                prompt,
                BigDecimal.TEN.intValue(),
                BigDecimal.ONE.floatValue());
        final var response = RetrofitUtils.executeCall(openAIClient.getCompletionsResult(request));

        System.out.println(request);

        if (Objects.nonNull(response) && !response.choices().isEmpty()) {
            var text = response.choices().get(0).text().strip();
            log.info("[OpenAI] Text Response: {}", text);
            return text.toUpperCase(Locale.ROOT).contains("YES");
        }else {
            log.warn("[OpenAI] Error Getting Response: {}", response);
        }
        return false;
    }
}
