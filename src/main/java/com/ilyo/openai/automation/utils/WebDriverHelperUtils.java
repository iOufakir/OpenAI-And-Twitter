package com.ilyo.openai.automation.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Slf4j
public class WebDriverHelperUtils {

  public static void scrollBy(final WebDriver webDriver, final int pixels) {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
    jsExecutor.executeScript("window.scrollBy(0, arguments[0]);", pixels);
    randomSleep(3000, 6000);
  }

  public static void randomSleep(int minMillis, int maxMillis) {
    try {
      int randomDelay = ThreadLocalRandom.current().nextInt(minMillis, maxMillis);
      Thread.sleep(randomDelay);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public static void humanLikeMouseMovement(WebDriver driver, WebElement targetElement) {
    Actions actions = new Actions(driver);
    Random random = new Random();

    int xOffset = random.nextInt(50) - 25; // Randomize a slight offset
    int yOffset = random.nextInt(50) - 25;

    // Simulate a small "curve" in movement
    for (int i = 0; i < 5; i++) {
      int intermediateX = xOffset / 5 * i;
      int intermediateY = yOffset / 5 * i;
      actions.moveByOffset(intermediateX, intermediateY).perform();
      try {
        Thread.sleep(random.nextInt(100)); // Add random delays
      } catch (InterruptedException e) {
        log.error(e.getMessage(), e);
      }
    }
    actions.moveToElement(targetElement).click().perform();
  }
}
