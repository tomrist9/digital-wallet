package com.ai.generated.tweet.to.kafka;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AIGeneratedTweetToKafkaServiceApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AIGeneratedTweetToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        log.info("Application is starting...");
//        boolean initResult = streamInitializer.init();
//        if (initResult) {
//            log.info("Starting AI Stream Runner with fixed rate {} seconds!", configData.getSchedulerDurationSec());
//            taskScheduler.scheduleAtFixedRate(aiStreamRunner,
//                    Duration.of(configData.getSchedulerDurationSec(), ChronoUnit.SECONDS));
//        } else {
//            log.error("Stream Initializer failed to initialize the streams! Not starting the AI Stream Runner!");
//        }
    }

}
