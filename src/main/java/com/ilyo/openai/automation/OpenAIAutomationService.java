package com.ilyo.openai.automation;

import static com.ilyo.openai.automation.utils.WebDriverHelperUtils.scrollBy;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OpenAIAutomationService {

  private static final String CHATGPT_DIV_SELECTOR = "#prompt-textarea";
  private static final String CHATGPT_DISCUSSION_SELECTOR = "div[title='Investing and money']";
  private static final String CHATGPT_URL = "https://chatgpt.com/c/6701856a-4604-8012-9a37-48a306bfee8c";

  private final WebDriver webDriver;

  @SneakyThrows
  public void sendPrompt(final String searchValue) {
    Thread.sleep(Duration.ofSeconds(10).toMillis());
    webDriver.get(CHATGPT_URL);

    final var wait = new WebDriverWait(webDriver, Duration.ofSeconds(12));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHATGPT_DIV_SELECTOR)));
    // To react as human
    scrollBy(webDriver, 800);
    webDriver.findElement(By.cssSelector(CHATGPT_DISCUSSION_SELECTOR)).click();

    writeAndSendPrompt(searchValue);
  }

  private void writeAndSendPrompt(String searchValue) {
    final var searchElement = webDriver.findElement(By.cssSelector(CHATGPT_DIV_SELECTOR));
    searchElement.click();
    final var jsDriver = (JavascriptExecutor) webDriver;
    jsDriver.executeScript("arguments[0].innerHTML = arguments[1]", searchElement.findElement(By.cssSelector("p")), searchValue);
  }


}
