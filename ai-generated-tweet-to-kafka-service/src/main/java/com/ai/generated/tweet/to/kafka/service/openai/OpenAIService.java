package com.ai.generated.tweet.to.kafka.service.openai;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.ai.generated.tweet.to.kafka.service.AIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OpenAIService implements AIService {
    private final AIGeneratedTweetToKafkaServiceConfigData configData;
    private final ObjectMapper objectMapper;
    @Override
    public String generateTweet() throws AIGeneratedTweetToKafkaServiceException {
        String prompt = configData.prompt().replace(configData.keywordsPlaceholder(),
                String.join(",", configData.streamingDataKeywords()));
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost request = getRequest(prompt);
            String response = httpClient.execute(request, resp-> EntityUtils.toString(request.getEntity()));
            return parseResponse(response);
        } catch (IOException e) {
            throw new AIGeneratedTweetToKafkaServiceException("Failed to generate tweet from OpenAI", e);
        }

    }
}
