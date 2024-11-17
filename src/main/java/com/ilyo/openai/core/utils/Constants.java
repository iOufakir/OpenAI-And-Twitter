package com.ilyo.openai.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String TWITTER_AUTHENTICATED_USER_ID = "1197918766996348928";

    public static final String TWITTER_TEXT_SEARCH_QUERY = "-has:mentions -is:retweet (-has:links OR has:media)";

    public static final String TWITTER_SEARCH_QUERY_AFFILIATE_PRODUCT = """
            (photo printer) lang:en -is:retweet -from:gadget_explorer
            """;

    public static final String AMAZON_AFFILIATE_TARGET_PRODUCT_NAME = "Wireless Photo Mini Printer";

    public static final String AMAZON_AFFILIATE_TARGET_PRODUCT_URL = "https://amzn.to/3LrwC4b";

}
