package com.ilyo.openai.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.ilyo.openai.util.Constants.OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final OpenAIService openAIService;


    @Async
    public boolean isHarmfulText(final String text) {
        return openAIService.isTextNegativeOrHarmful(OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL
                .formatted(text));
    }


}
