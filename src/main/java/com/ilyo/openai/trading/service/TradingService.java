package com.ilyo.openai.trading.service;

import static com.ilyo.openai.automation.GoogleNewsExtractionService.GOOGLE_CRYPTO_NEWS_URL;
import static com.ilyo.openai.automation.GoogleNewsExtractionService.GOOGLE_NEWS_URL;

import java.util.List;
import java.util.stream.Stream;

import com.ilyo.openai.automation.BinanceExtractionService;
import com.ilyo.openai.automation.GoogleNewsExtractionService;
import com.ilyo.openai.automation.OpenAIAutomationService;
import com.ilyo.openai.automation.TwitterExtractionService;
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

  public void startCryptoTrading(final String token) {
    log.info("Start trading with token {}", token);
    final var fearGreedIndex = binanceExtractionService.getFearGreedIndex();
    openAIAutomationService.sendPrompt("hello");

//    getGoogleNews(token).forEach(newsText -> {
//      final var prompt = MessageFormat.format(AI_PROMPT_CRYPTO_TRADING_USER, newsText, "article",
//          fearGreedIndex.todayIndex(), fearGreedIndex.yesterdayIndex(), fearGreedIndex.lastWeekIndex());
//      final var userChatMessage = new ChatMessage(USER.name().toLowerCase(), prompt);
//      final var systemChatMessage = new ChatMessage(SYSTEM.name().toLowerCase(), AI_PROMPT_CRYPTO_TRADING_SYSTEM);
//      //var message = openAIService.sendPrompt(List.of(systemChatMessage, userChatMessage), OPENAI_LONG_TEXT_MAX_TOKENS, OPENAI_FOCUSED_TEMPERATURE);
//    });

//    final String tweets = getAndAppendTweets(token);
//    final var prompt = MessageFormat.format(AI_PROMPT_CRYPTO_TRADING_USER, token, tweets, "tweets",
//        fearGreedIndex.todayIndex(), fearGreedIndex.yesterdayIndex(), fearGreedIndex.lastWeekIndex());
//    openAIAutomationService.sendPrompt(prompt);
  }

  private String getAndAppendTweets(final String token) {
    final StringBuilder tweetsBuilder = new StringBuilder();
    final var tweetsList = twitterExtractionService.extractTweets(token, true);
    for (int i = 0; i < tweetsList.size(); i++) {
      final var tweet = tweetsList.get(i);
      tweetsBuilder.append("%s) Tweet %s: %s %n%n".formatted(i + 1, i + 1,tweet.text()));
    }
    return tweetsBuilder.toString();
  }

  private List<String> getGoogleNews(final String token) {
    final var cryptoNews = googleNewsExtractionService.extractGoogleNews(GOOGLE_CRYPTO_NEWS_URL.formatted(token), 1);
    final var economyNews = googleNewsExtractionService.extractGoogleNews(GOOGLE_NEWS_URL.formatted("economy"), 1);
    final var marketNews = googleNewsExtractionService.extractGoogleNews(GOOGLE_NEWS_URL.formatted("crypto market"), 1);

    return Stream.of(cryptoNews, economyNews, marketNews)
        .flatMap(List::stream)
        .toList();
  }

}
