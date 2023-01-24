package com.ilyo.openai.scheduling;

import com.ilyo.openai.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
@Slf4j
public class AppScheduler {

    private final UserService userService;

    @Scheduled(cron = "${app.schedule.twitter-fixed-delay-expression}")
    public void startAppScheduler() {
        log.info("[Schedule] Current execution time: {}", LocalDateTime.now());
        userService.launchAutoTweet();
    }

}
