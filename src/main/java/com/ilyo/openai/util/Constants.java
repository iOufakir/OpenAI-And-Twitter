package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL =
            """
                    Decide whether the following comment contains bad, offensive, harmful or harassment words, please ONLY answer with one word "YES" or "NO".
                                        
                    %s.
                                        

                    """;

}
