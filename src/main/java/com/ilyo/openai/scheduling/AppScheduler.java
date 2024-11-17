package com.ilyo.openai.scheduling;

import java.time.LocalDateTime;

import com.ilyo.openai.core.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class AppScheduler {

    private final UserService userService;

    //@Scheduled(cron = "${app.schedule.twitter-post-cron-expression}")
    public void startAppScheduler() {
        log.info("[Auto Publish] Current execution time: {}", LocalDateTime.now());
        userService.launchTwitterAutoPublish();
    }

    //@Scheduled(cron = "${app.schedule.twitter-replies-cron-expression}")
    public void startAutoRepliesScheduler() {
        log.info("[Schedule Auto Replies] Current execution time: {}", LocalDateTime.now());
        userService.launchTwitterAutoReplies();
    }

    //@Scheduled(cron = "${app.schedule.twitter-amazon-affiliate-program-cron-expression}")
    public void startAmazonAffiliateScheduler() {
        userService.launchAmazonAffiliateProgram();
    }

}
