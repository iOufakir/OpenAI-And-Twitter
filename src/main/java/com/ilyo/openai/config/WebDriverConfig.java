package com.ilyo.openai.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.SecureRandom;

@Configuration
public class WebDriverConfig {

    @Bean
    public WebDriver webDriver() {
        return new FirefoxDriver(buildLocalFirefoxOptions());
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

    private FirefoxOptions buildLocalFirefoxOptions() {
        System.setProperty("webdriver.gecko.driver", "local/geckodriver");
        var options = new FirefoxOptions();
        options.setHeadless(true);
        options.setLogLevel(FirefoxDriverLogLevel.FATAL);
        options.addArguments("--start-maximized");
        options.addArguments("--enable-javascript");
        options.addArguments("--profile",
                "/Users/ilyasdev/Library/Application Support/Firefox/Profiles/ja0iu3ci.default-release");
        return options;
    }

}
