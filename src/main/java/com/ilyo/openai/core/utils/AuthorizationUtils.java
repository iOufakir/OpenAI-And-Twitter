package com.ilyo.openai.core.utils;

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

    public static String createBasicAuthorizationHeader(final String key, String password) {
        var toEncodeToBase64 = "%s:%s".formatted(key, password);
        return createBasicToken(Base64.getEncoder().encodeToString(toEncodeToBase64.getBytes(StandardCharsets.UTF_8)));
    }

}
