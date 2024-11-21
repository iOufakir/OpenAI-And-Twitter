package com.ilyo.openai.automation.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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
}
