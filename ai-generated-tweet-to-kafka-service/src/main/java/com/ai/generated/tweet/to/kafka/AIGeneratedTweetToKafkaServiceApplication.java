package com.ai.generated.tweet.to.kafka;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AIGeneratedTweetToKafkaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIGeneratedTweetToKafkaServiceApplication.class, args);
    }

    @PostConstruct
    public void init(){

    }
}
