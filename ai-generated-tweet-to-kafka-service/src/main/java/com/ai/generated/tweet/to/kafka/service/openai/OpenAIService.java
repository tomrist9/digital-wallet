package com.ai.generated.tweet.to.kafka.service.openai;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.ai.generated.tweet.to.kafka.service.AIService;
import com.ai.generated.tweet.to.kafka.service.openai.model.OpenAIRequest;
import com.ai.generated.tweet.to.kafka.service.openai.model.OpenAIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ai-tweet-generator.ai-service", havingValue = "OpenAI")
public class OpenAIService implements AIService {
    private final AIGeneratedTweetToKafkaServiceConfigData configData;
    private final ObjectMapper objectMapper;

    @Override
    public String generateTweet() throws AIGeneratedTweetToKafkaServiceException {
        String prompt = configData.prompt().replace(configData.keywordsPlaceholder(),
                String.join(",", configData.streamingDataKeywords()));
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = getRequest(prompt);
            String response = httpClient.execute(request, resp -> EntityUtils.toString(resp.getEntity()));
            return parseResponse(response);
        } catch (IOException e) {
            throw new AIGeneratedTweetToKafkaServiceException("Failed to generate tweet from OpenAI", e);
        }

    }


    private HttpPost getRequest(String prompt) throws JsonProcessingException {

        HttpPost request = new HttpPost(configData.openAI().getUrl());

        request.addHeader(HttpHeaders.CONTENT_TYPE, configData.openAI().getContentType());
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + configData.openAI().getApiKey());

        List<OpenAIRequest.Message> messages =
                configData.openAI().getMessages().stream()
                        .map(message ->
                                OpenAIRequest.Message.builder()
                                        .role(message.getRole())
                                        .content(List.of(
                                                OpenAIRequest.Content.builder()
                                                        .type("text")
                                                        .text(prompt)
                                                        .build()
                                        ))
                                        .build()
                        )
                        .collect(Collectors.toList());

        OpenAIRequest openAIRequest = OpenAIRequest.builder()
                .model(configData.openAI().getModel())
                .max_completion_tokens(configData.openAI().getMaxCompletionTokens())
                .temperature(configData.openAI().getTemperature())
                .messages(messages)
                .build();

        request.setEntity(new StringEntity(objectMapper.writeValueAsString(openAIRequest)));

        return request;
    }
    private String parseResponse(String response) throws JsonProcessingException {

        log.info("OpenAI raw response: {}", response);

        OpenAIResponse openAIResponse =
                objectMapper.readValue(response, OpenAIResponse.class);

        if (openAIResponse.getChoices() == null || openAIResponse.getChoices().isEmpty()) {
            throw new RuntimeException("OpenAI returned no choices: " + response);
        }

        return openAIResponse.getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}
