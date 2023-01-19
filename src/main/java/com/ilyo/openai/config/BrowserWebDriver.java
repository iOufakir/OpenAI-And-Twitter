package com.ilyo.openai.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class BrowserWebDriver {

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.gecko.driver", "local/geckodriver");

        var options = new FirefoxOptions();
        options.setHeadless(true);
        options.setLogLevel(FirefoxDriverLogLevel.FATAL);
        options.addArguments("--start-maximized");
        options.addArguments("--enable-javascript");
        options.addArguments("--profile",
                "/Users/ilyasdev/Library/Application Support/Firefox/Profiles/ja0iu3ci.default-release");
        return new FirefoxDriver(options);
    }


    @Bean
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }
}
