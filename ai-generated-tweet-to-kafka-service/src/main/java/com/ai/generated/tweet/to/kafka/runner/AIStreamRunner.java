package com.ai.generated.tweet.to.kafka.runner;


import com.ai.generated.tweet.to.kafka.service.AIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AIStreamRunner implements Runnable{
    private final AIService aiService;
    @Override
    public void run() {
    String generatedTweet = aiService.generateTweet();
    log.info("Generated tweet: {}", generatedTweet);
    }
}