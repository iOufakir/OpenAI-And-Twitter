package com.ilyo.openai.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationUtils {

    public static String createBearerToken(String token) {
        return "%s %s".formatted("Bearer", token);
    }

    public static String createBasicToken(String token) {
        return "%s %s".formatted("Basic", token);
    }
}
