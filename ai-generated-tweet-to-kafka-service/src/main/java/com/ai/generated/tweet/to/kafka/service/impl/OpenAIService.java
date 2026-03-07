package com.ai.generated.tweet.to.kafka.service.impl;

import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.ai.generated.tweet.to.kafka.service.AIService;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService implements AIService {
    @Override
    public String generateTweet() throws AIGeneratedTweetToKafkaServiceException {
        return "AI Generated Tweet Content";
    }
}
