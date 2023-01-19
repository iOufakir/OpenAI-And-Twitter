package com.ilyo.openai.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.ilyo.openai.util.Constants.TWITTER_AUTHORIZE_APP_BUTTON_XPATH;

@Service
@AllArgsConstructor
public class MyWebClient {
    private final WebDriver webDriver;

    @SneakyThrows
    public String extractTwitterOAuth2Code(final String url, final String callbackUrl){
        webDriver.get(url);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(12));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TWITTER_AUTHORIZE_APP_BUTTON_XPATH)));
        WebElement button = webDriver.findElement(By.xpath(TWITTER_AUTHORIZE_APP_BUTTON_XPATH));
        button.click();

        // wait for the redirect
        wait.until(ExpectedConditions.urlContains(callbackUrl));
        wait.until(ExpectedConditions.urlContains("code="));
        final var currentUrl = webDriver.getCurrentUrl();
        return currentUrl.split("code=")[1];
    }
}
