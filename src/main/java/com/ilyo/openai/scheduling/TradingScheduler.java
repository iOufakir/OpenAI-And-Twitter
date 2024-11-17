package com.ilyo.openai.scheduling;

import java.time.LocalDateTime;

import com.ilyo.openai.core.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class TradingScheduler {

  private final UserService userService;

  //@Scheduled(cron = "${app.schedule.twitter-post-cron-expression}")
  public void startAppScheduler() {
    log.info("[Auto Publish] Current execution time: {}", LocalDateTime.now());
    userService.launchTwitterAutoPublish();
  }

}
