package com.ilyo.openai.service;

import com.ilyo.openai.external.twitter.dto.Tweet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ilyo.openai.service.TwitterService.getUser;
import static com.ilyo.openai.util.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final OpenAIService openAIService;


    public boolean isHarmfulText(final String text) {
        return openAIService.isTextNegativeOrHarmful(OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL.formatted(text, OPENAI_PROMPT_PREFIX));
    }


}
