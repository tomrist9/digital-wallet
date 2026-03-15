package com.ai.generated.tweet.to.kafka.service;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import lombok.RequiredArgsConstructor;
import com.openai.client.OpenAIClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(value = "ai-tweet-generator.ai-service", havingValue = "OpenAI-JavaClient")
@RequiredArgsConstructor
public class OpenAIJavaClient implements AIService {
    private final AIGeneratedTweetToKafkaServiceConfigData configData;
    @Override
    public String generateTweet() throws AIGeneratedTweetToKafkaServiceException {
        String prompt = configData.prompt().replace(configData.keywordsPlaceholder(),
                String.join(",", configData.streamingDataKeywords()));
        return getPromptResponse(prompt);
    }

    private String getPromptResponse(String prompt) {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();
    }
}
