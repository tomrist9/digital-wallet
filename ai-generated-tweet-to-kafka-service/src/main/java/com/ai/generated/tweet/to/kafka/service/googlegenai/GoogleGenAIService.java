package com.ai.generated.tweet.to.kafka.service.googlegenai;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.ai.generated.tweet.to.kafka.service.AIService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@ConditionalOnProperty(value = "ai-tweet-generator.ai-service", havingValue = "GoogleGenAI")
public class GoogleGenAIService implements AIService {
    private final AIGeneratedTweetToKafkaServiceConfigData configData;
    private final Client googleGenAIClient;

    public GoogleGenAIService(AIGeneratedTweetToKafkaServiceConfigData configData) {
        this.configData = configData;
        this.googleGenAIClient = Client.builder()
//                .project(configData.googleGenAI().getProjectId())
//                .location(configData.googleGenAI().getLocation())
                .apiKey(configData.googleGenAI().getApiKey())
//                .vertexAI(true)
                .build();
    }

    @PreDestroy
    public void close() {
        if (this.googleGenAIClient != null) {
            this.googleGenAIClient.close();
        }
    }

    @Override
    public String generateTweet() throws AIGeneratedTweetToKafkaServiceException {
        log.info("Generating tweet using GoogleGenAIService");
        String prompt = configData.prompt().replace(configData.keywordsPlaceholder(),
                String.join(",", configData.streamingDataKeywords()));
        return getPromptResponse(prompt);
    }

    private String getPromptResponse(String prompt) {
        try {
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .maxOutputTokens(200)
                    .temperature(1.0f)
                    .build();

            String modelName = configData.googleGenAI().getModelName();

            GenerateContentResponse response =
                    googleGenAIClient.models.generateContent(modelName, prompt, config);

            String result = response.text();

            System.out.println(" GENERATED: " + result);

            return result;

        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            e.printStackTrace();
            return "ERROR";
        }
    }
}