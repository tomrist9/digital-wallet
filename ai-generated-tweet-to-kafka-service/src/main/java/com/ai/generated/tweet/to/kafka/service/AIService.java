package com.ai.generated.tweet.to.kafka.service;

import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;

public interface AIService {
    String generateTweet() throws AIGeneratedTweetToKafkaServiceException;
}
