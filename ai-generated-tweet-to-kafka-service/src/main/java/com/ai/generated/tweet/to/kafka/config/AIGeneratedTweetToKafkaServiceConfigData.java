package com.ai.generated.tweet.to.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@ConfigurationProperties(prefix = "ai-generated-tweet-to-kafka-service")
public record AIGeneratedTweetToKafkaServiceConfigData(
        List<String> streamingDataKeywords
) {}
