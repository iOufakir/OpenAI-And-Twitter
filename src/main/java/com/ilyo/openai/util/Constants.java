package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String TWITTER_AUTHENTICATED_USER_ID = "1197918766996348928";

    public static final String TWITTER_TEXT_SEARCH_QUERY = "-has:mentions -is:retweet (-has:links OR has:media)";

    public static final String TWITTER_SEARCH_QUERY_AFFILIATE_PRODUCT = """
            ("Portable Monitor" OR "monitor screen" OR "Touch screen" OR "Computer Display") lang:en -is:retweet -from:gadget_explorer
            """;

    public static final String AMAZON_AFFILIATE_TARGET_PRODUCT_NAME = "Portable Monitor";

    public static final String AMAZON_AFFILIATE_TARGET_PRODUCT_URL = "https://amzn.to/3YI3j0c";

    public static final String TWITTER_SEARCH_QUERY_IF_SHOULD_PROMOTE_PRODUCT = """
            Only Answer with 'YES' if you think It's a good idea to add a promoting tweet about a '%s' product for the following tweet, otherwise answer with 'NO'.

            Here is the tweet: %s.
            """;

    public static final String TWITTER_AUTHORIZE_APP_BUTTON_XPATH =
            "//div[@data-testid='OAuth_Consent_Button']";

    public static final String OPENAI_PROMPT_WRITE_TWEET = "Write a tweet as a real Human based for this tweet: %s";

    public static final String OPENAI_PROMPT_REPLY_TO_TWEET = "Write a short reply to this tweet as a real human without using hashtags: %s";

    public static final String OPENAI_PROMPT_PROMOTE_AFFILIATE_PRODUCT = """
            Write a creative reply for the following user tweet, without hashtags, make it short and creative, write the reply without using 'Reply' word in the response, don't make the reply sound like an Ads. Then promote a product about '%s' by adding this URL to the reply: %s
                        
            Here is the user tweet: %s.
            """;

}
