package com.ilyo.openai.service;


import com.vader.sentiment.analyzer.SentimentAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentimentService {

    public boolean isTextNegative(final String text) {
        final var sentimentPolarities = SentimentAnalyzer.getScoresFor(text);
        log.info("[Sentiment Analysis] Response for text: {} => {}", text, sentimentPolarities);

        if (sentimentPolarities.getPositivePolarity() > 0 && sentimentPolarities.getNegativePolarity() == 0) {
            return false;
        } else {
            return sentimentPolarities.getNegativePolarity() > 0 || sentimentPolarities.getCompoundPolarity() > -0.1f;
        }
    }
}
