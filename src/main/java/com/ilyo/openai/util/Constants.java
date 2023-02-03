package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL =
            """
                    Answer with "YES", if the following social media comment potentially insulting, offensive or harmful to an individual? [%s].

                          """;

}
