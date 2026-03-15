package com.ai.generated.tweet.to.kafka.service;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessage;
import lombok.RequiredArgsConstructor;
import com.openai.client.OpenAIClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@ConditionalOnProperty(value = "ai-tweet-generator.ai-service", havingValue = "OpenAI-JavaClient")
@RequiredArgsConstructor
@Service
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

        ChatCompletionCreateParams.Builder createParams = ChatCompletionCreateParams.builder()
                .model(ChatModel.of(configData.openAI().getModel()))
                .addDeveloperMessage("Generate a tweet based on the specified format and the given keywords.")
                .maxCompletionTokens(configData.openAI().getMaxCompletionTokens())
                .temperature(configData.openAI().getTemperature())
                .addUserMessage(prompt);

        List<ChatCompletionMessage> messages = client.chat()
                .completions()
                .create(createParams.build())
                .choices()
                .stream()
                .map(ChatCompletion.Choice::message)
                .toList();

        return messages.get(0).content().get();
    }
}
