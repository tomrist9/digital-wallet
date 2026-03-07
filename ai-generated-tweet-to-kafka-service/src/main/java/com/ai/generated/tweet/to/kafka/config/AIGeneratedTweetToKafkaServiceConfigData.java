package com.ai.generated.tweet.to.kafka.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;


@ConfigurationProperties(prefix = "ai-generated-tweet-to-kafka-service")
public record AIGeneratedTweetToKafkaServiceConfigData(
        List<String> streamingDataKeywords, Long schedulerDurationSec
) {}
