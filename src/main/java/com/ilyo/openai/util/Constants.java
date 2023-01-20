package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String TWITTER_SEARCH_QUERY = "%s (%s)";

    public static final String TWITTER_TEXT_SEARCH_QUERY = "-has:media -is:retweet";

    public static final String TWITTER_AUTHORIZE_APP_BUTTON_XPATH =
            "//div[@data-testid='OAuth_Consent_Button']";

}
