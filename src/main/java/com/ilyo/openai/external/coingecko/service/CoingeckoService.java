package com.ilyo.openai.external.coingecko.service;

import static com.ilyo.openai.core.enums.Currency.USD;

import com.ilyo.openai.core.enums.CoinToken;
import com.ilyo.openai.core.utils.RetrofitUtils;
import com.ilyo.openai.external.coingecko.client.CoingeckoApiClient;
import com.ilyo.openai.external.coingecko.dto.HistoricalDataSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoingeckoService {

  private static final String DEFAULT_INTERVAL = "daily";

  private final CoingeckoApiClient coingeckoApiClient;

  public HistoricalDataSummary getHistoricalDataSummary(final CoinToken coinToken, final int days, final int precision) {
    var response =
        RetrofitUtils.executeCall(coingeckoApiClient.getHistoricalChartData(coinToken.getId(), USD.name().toLowerCase(), days, DEFAULT_INTERVAL, precision));

    final var prices = response.prices();

    final var todayPrice = prices.get(prices.size() - 1).get(1);
    final var yesterdayPrice = prices.get(prices.size() - 2).get(1);
    final var lastWeekPrice = prices.get(0).get(1);
    return new HistoricalDataSummary(todayPrice, yesterdayPrice, lastWeekPrice);
  }

}
