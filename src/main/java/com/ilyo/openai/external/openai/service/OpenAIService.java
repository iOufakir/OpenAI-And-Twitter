package com.ilyo.openai.external.openai.service;

import java.util.List;
import java.util.Objects;

import com.ilyo.openai.core.utils.RetrofitUtils;
import com.ilyo.openai.external.openai.client.OpenAIClient;
import com.ilyo.openai.external.openai.config.OpenAIConfig;
import com.ilyo.openai.external.openai.dto.ChatCompletionsRequest;
import com.ilyo.openai.external.openai.dto.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OpenAIService {
  private final OpenAIClient openAIClient;
  private final OpenAIConfig openAIConfig;

  public String sendPrompt(final List<ChatMessage> chatMessages, int maxTokens, float temperature) {
    var request = new ChatCompletionsRequest(openAIConfig.model(),
        chatMessages,
        maxTokens,
        temperature);
    var response = RetrofitUtils.executeCall(openAIClient.getChatCompletionsResult(request));

    if (Objects.nonNull(response) && !response.choices().isEmpty()) {
      var text = response.choices().get(0).message().content().strip();
      log.info("[OpenAI] Text Response: {}", text);
      return text;
    }
    log.info("[OpenAI] No response has been generated!");
    return "";
  }

}
