package com.ilyo.openai.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;

@Configuration
public class WebDriverConfig {

    @Profile("local")
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
    public WebDriver remoteWebDriver() throws MalformedURLException, UnknownHostException {
        var options = new FirefoxOptions();
        options.setHeadless(true);
        options.addArguments("--start-maximized");
        options.addArguments("--enable-javascript");
        options.setCapability("browserName", "firefox");
        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("pageLoadStrategy", "normal");
        options.setCapability("platformName", "LINUX");
        options.setCapability("unhandledPromptBehavior", "dismiss");

        return new RemoteWebDriver(new URL("http://firefox_service:4444/wd/hub"), options);
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

}
