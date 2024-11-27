package com.ilyo.openai.automation.config;

import java.security.SecureRandom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    final var options = new FirefoxOptions();
    options.addPreference("network.http.phishy-userpass-length", 255); // Bypass phishing and malware checks
    options.addPreference("media.navigator.enabled", false); // Hide navigator properties
    options.addPreference("dom.webdriver.enabled", false); // Hide WebDriver
    options.addPreference("useAutomationExtension", false); // Disable automation extension
    options.addPreference("general.useragent.override", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:132.0) Gecko/20100101 Firefox/132.0");

    options.addArguments("--headless");
    options.addArguments("--disable-blink-features=AutomationControlled"); // Make Selenium undetectable
    options.addArguments("--start-maximized");
    options.addArguments("--enable-javascript");
    options.addArguments("--profile", "/Users/ilyasdev/Library/Application Support/Firefox/Profiles/ja0iu3ci.default-release");
    return options;
  }

}
