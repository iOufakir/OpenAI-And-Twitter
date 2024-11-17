package com.ilyo.openai.trading.controller;


import com.ilyo.openai.trading.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trading")
@RequiredArgsConstructor
public class TradingController {

  private final TradingService tradingService;

  @PostMapping("/crypto")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void tradeCrypto(@RequestParam String token) {
    tradingService.startCryptoTrading(token);
  }

}
