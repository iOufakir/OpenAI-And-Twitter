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

    @Scheduled(cron = "${app.schedule.twitter-post-cron-expression}")
    public void startAppScheduler() {
        log.info("[Schedule] Current execution time: {}", LocalDateTime.now());
        userService.launchAutoTweet();
    }

    @Scheduled(cron = "${app.schedule.twitter-replies-cron-expression}")
    public void startAutoRepliesScheduler() {
        log.info("[Schedule Auto Replies] Current execution time: {}", LocalDateTime.now());
        userService.launchAutoReplies();
    }

}
