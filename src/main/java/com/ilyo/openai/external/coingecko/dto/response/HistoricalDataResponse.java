package com.ilyo.openai.external.coingecko.dto.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HistoricalDataResponse(List<List<Double>> prices,
                                     @JsonProperty("market_caps") List<List<Double>> marketCaps,
                                     @JsonProperty("total_volumes") List<List<Double>> totalVolumes) {

}
