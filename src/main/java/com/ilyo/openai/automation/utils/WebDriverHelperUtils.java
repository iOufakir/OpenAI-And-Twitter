package com.ilyo.openai.automation.utils;

import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class WebDriverHelperUtils {

  @SneakyThrows(InterruptedException.class)
  public static void scrollBy(final WebDriver webDriver, final int pixels) {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
    jsExecutor.executeScript("window.scrollBy(0, arguments[0]);", pixels);
    Thread.sleep(6000);
  }

}
