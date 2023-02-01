package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL =
            """
                   Is the following comment text potentially harmful, offensive, or contains harassment language? (ONLY Answer with "YES" or "NO").
                                        
                    %s.


                    """;

}
