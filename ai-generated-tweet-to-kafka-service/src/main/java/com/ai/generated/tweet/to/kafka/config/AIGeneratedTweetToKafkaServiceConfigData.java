package com.ai.generated.tweet.to.kafka.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;


@ConfigurationProperties(prefix = "ai-generated-tweet-to-kafka-service")
public record AIGeneratedTweetToKafkaServiceConfigData(

        List<String> streamingDataKeywords,
        Long schedulerDurationSec,
        String prompt,
        String keywordsPlaceholder,
        OpenAI openAI,
        GoogleGenAI googleGenAI

) {

    @Data
    public static class OpenAI {
        private String url;
        private String apiKey;
        private String contentType;
        private String model;
        private Integer maxCompletionTokens;
        private Double temperature;
        private List<Message> messages;
    }

    @Data
    public static class Message {
        private String role;
        private List<Content> content;
    }

    @Data
    public static class Content {
        private String type;
        private String text;
    }

    @Data
    public static class GoogleGenAI {
        private String projectId;
        private String location;
        private String modelName;
        private Integer maxOutputTokens;
        private Float temperature;
        private Integer candidateCount;
    }
}
