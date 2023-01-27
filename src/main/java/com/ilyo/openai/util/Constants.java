package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String TWITTER_SEARCH_QUERY = "%s (%s)";

    public static final String TWITTER_TEXT_SEARCH_QUERY = "-has:mentions -is:retweet (-has:links OR has:media)";

    public static final String TWITTER_AUTHORIZE_APP_BUTTON_XPATH =
            "//div[@data-testid='OAuth_Consent_Button']";

    public static final String OPENAI_PROMPT_WRITE_TWEET = "Write a tweet as a real Human based for this tweet: %s";

    public static final String OPENAI_PROMPT_REPLY_TO_TWEET = "Write a short reply to this tweet as a real human without using hashtags: %s";

    public static final String OPENAI_PROMPT_PREFIX = "\n\n";
    public static final String OPENAI_PROMPT_DETECT_TEXT_IF_NEGATIVE_OR_HARMFUL =
            "ONLY answer with YES or NO, does the following comment contains harmful, negative or harassment? %s %s";

}
