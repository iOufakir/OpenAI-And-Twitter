package com.ilyo.openai.scheduling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan("com.ilyo.openai.scheduling")
public class ScheduledConfig {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
