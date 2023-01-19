package com.ilyo.openai.dto;


import java.time.LocalDateTime;

public record TweetResponse(String id,
                            LocalDateTime createdAt,
                            String text,
                            String currencyToBuy,
                            String name,
                            String username) {

}
