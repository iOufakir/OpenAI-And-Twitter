package com.ilyo.openai.trading.service;

import static com.ilyo.openai.automation.GoogleNewsExtractionService.GOOGLE_SPECIFIC_NEWS_URL;
import static com.ilyo.openai.automation.GoogleNewsExtractionService.GOOGLE_NEWS_URL;
import static com.ilyo.openai.core.utils.OpenAIUtils.AI_PROMPT_CRYPTO_TRADING_USER;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import com.ilyo.openai.automation.BinanceExtractionService;
import com.ilyo.openai.automation.GoogleNewsExtractionService;
import com.ilyo.openai.automation.OpenAIAutomationService;
import com.ilyo.openai.automation.TwitterExtractionService;
import com.ilyo.openai.automation.dto.FearGreedIndex;
import com.ilyo.openai.core.enums.CoinToken;
import com.ilyo.openai.core.enums.PromptType;
import com.ilyo.openai.external.coingecko.dto.HistoricalDataSummary;
import com.ilyo.openai.external.coingecko.service.CoingeckoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingService {

  private final GoogleNewsExtractionService googleNewsExtractionService;
  private final TwitterExtractionService twitterExtractionService;
  private final BinanceExtractionService binanceExtractionService;
  private final OpenAIAutomationService openAIAutomationService;
  private final CoingeckoService coingeckoService;

  public void startCryptoTrading(final CoinToken token) {
    log.info("Start trading with token {}", token);
    final var fearGreedIndex = binanceExtractionService.getFearGreedIndex();
    final var historicalDataSummary = coingeckoService.getHistoricalDataSummary(token, 10, 5);

    final String tweets = getAndAppendTweets(token);
    sendPrompt(token, tweets, "tweets", fearGreedIndex, historicalDataSummary);

    getGoogleNews(token).forEach(newsText -> sendPrompt(token, newsText, "article", fearGreedIndex, historicalDataSummary));
  }

  private void sendPrompt(CoinToken token, String text, String targetText, FearGreedIndex fearGreedIndex, HistoricalDataSummary historicalDataSummary) {
    final var prompt = MessageFormat.format(AI_PROMPT_CRYPTO_TRADING_USER, token, text, targetText,
        fearGreedIndex.todayIndex(), fearGreedIndex.yesterdayIndex(), fearGreedIndex.lastWeekIndex(),
        historicalDataSummary.todayPrice(), historicalDataSummary.yesterdayPrice(), historicalDataSummary.lastWeekPrice());

    final var promptType = targetText.equals("tweets") ? PromptType.TWEETS : PromptType.NEWS;
    openAIAutomationService.sendPrompt(prompt, promptType);
  }

  private String getAndAppendTweets(final CoinToken token) {
    final StringBuilder tweetsBuilder = new StringBuilder();
    final var tweetsList = twitterExtractionService.extractTweets(token.name(), true);
    for (int i = 0; i < tweetsList.size(); i++) {
      final var tweet = tweetsList.get(i);
      tweetsBuilder.append("%s) Tweet Nº%s: %s %n%n".formatted(i + 1, i + 1, tweet.text()));
    }
    return tweetsBuilder.toString();
  }

  private List<String> getGoogleNews(final CoinToken token) {
    final var cryptoNews = googleNewsExtractionService.extractGoogleNews(GOOGLE_SPECIFIC_NEWS_URL.formatted(token.getFullName()), 1);
    //final var economyNews = googleNewsExtractionService.extractGoogleNews(GOOGLE_NEWS_URL.formatted("economy"), 1);
    final var marketNews = googleNewsExtractionService.extractGoogleNews(GOOGLE_NEWS_URL.formatted("crypto market"), 1);

    return Stream.of(cryptoNews, marketNews)
        .flatMap(List::stream)
        .toList();
  }

}
