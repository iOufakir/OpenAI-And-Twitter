package com.ilyo.openai.automation;

import static com.ilyo.openai.automation.utils.WebDriverHelperUtils.randomSleep;
import static com.ilyo.openai.automation.utils.WebDriverHelperUtils.scrollBy;

import java.security.SecureRandom;
import java.time.Duration;

import com.ilyo.openai.core.enums.PromptType;
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
  private static final String CHATGPT_TWEETS_DISCUSSION_SELECTOR = "div[title='Investing using tweets']";
  private static final String CHATGPT_NEWS_DISCUSSION_SELECTOR = "div[title='Investing using news']";
  private static final String CHATGPT_SEND_BUTTON_SELECTOR = "button[data-testid='send-button']";
  private static final String CHATGPT_URL = "https://chatgpt.com/c/6701856a-4604-8012-9a37-48a306bfee8c";

  private final WebDriver webDriver;
  private final SecureRandom secureRandom;

  @SneakyThrows
  public void sendPrompt(final String searchValue, final PromptType promptType) {
    webDriver.get(CHATGPT_URL);
    // To react as human
    scrollBy(webDriver, secureRandom.nextInt(500, 800));
    randomSleep(2000, 7000);

    final var wait = new WebDriverWait(webDriver, Duration.ofSeconds(secureRandom.nextInt(5, 12)));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHATGPT_DIV_SELECTOR)));
    // To react as human
    scrollBy(webDriver, secureRandom.nextInt(500, 801));
    final var discussion = webDriver.findElement(By.cssSelector(promptType == PromptType.TWEETS ? CHATGPT_TWEETS_DISCUSSION_SELECTOR : CHATGPT_NEWS_DISCUSSION_SELECTOR));
    discussion.click();

    writeAndSendPrompt(searchValue);
  }

  private void writeAndSendPrompt(final String searchValue) {
    // To react as human
    scrollBy(webDriver, secureRandom.nextInt(100, 400));
    randomSleep(1200, 3500);
    final var searchElement = webDriver.findElement(By.cssSelector(CHATGPT_DIV_SELECTOR));
    searchElement.click();
    randomSleep(500, 1500);

    final var jsDriver = (JavascriptExecutor) webDriver;
    final var htmlSearchValue = searchValue.replace("\n", "<p><br class=\"ProseMirror-trailingBreak\"/></p>");
    jsDriver.executeScript("arguments[0].innerHTML = arguments[1]", searchElement, htmlSearchValue);
    randomSleep(2400, 4400);
    webDriver.findElement(By.cssSelector(CHATGPT_SEND_BUTTON_SELECTOR)).click();
    log.info("[OpenAI] Prompt has been sent successfully {}", searchValue);
    // Wait for the response, this will help to act as a human
    randomSleep(8000, 12_000);
  }


}
