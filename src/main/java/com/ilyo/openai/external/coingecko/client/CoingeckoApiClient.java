package com.ilyo.openai.external.coingecko.client;

import com.ilyo.openai.external.coingecko.dto.response.HistoricalDataResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoingeckoApiClient {


  @GET("v3/coins/{id}/market_chart")
  Call<HistoricalDataResponse> getHistoricalChartData(@Path("id") String id,
                                                      @Query("vs_currency") String vsCurrency,
                                                      @Query("days") int days,
                                                      @Query("interval") String interval,
                                                      @Query("precision") int precision);

}
