package com.ilyo.openai.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextCleanerUtils {

  public static String cleanText(final String text) {
    return text.replaceAll("\\n", " ")
        .replaceAll("\\s{2,}", " ") // Replace multiple spaces with a single space
        .strip();
  }

}
