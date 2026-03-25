package com.ai.generated.tweet.to.kafka.service.springai;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.exception.AIGeneratedTweetToKafkaServiceException;
import com.ai.generated.tweet.to.kafka.service.AIService;
import com.ai.generated.tweet.to.kafka.service.springai.model.TweetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@ConditionalOnProperty(value = "ai-tweet-generator.ai-service", havingValue = "SpringAI-OpenAI")
@Service
@RequiredArgsConstructor
public class SpringAIOpenAIService implements AIService {

    private final ChatClient client;
    private final AIGeneratedTweetToKafkaServiceConfigData configData;

    @Value("classpath:/templates/tweet-prompt.st")
    private  Resource tweetPrompt;

    @Override
    public String generateTweet() throws AIGeneratedTweetToKafkaServiceException {

        BeanOutputConverter<TweetResponse> converter = new BeanOutputConverter<>(TweetResponse.class);
        log.info("Converter format :{}", converter.getFormat());
        PromptTemplate promptTemplate = new PromptTemplate(tweetPrompt);
        Prompt prompt = promptTemplate.create(Map.of(
                configData.keywordsPlaceholder().replace("{", "").replace("}", ""),
                String.join(",", configData.streamingDataKeywords()),
                "format",
                converter.getFormat()
        ));

        String modelResult = client.prompt(prompt)
                .call()
                .content();

        log.info("Model result: {}", modelResult);

        return modelResult;
    }

}
