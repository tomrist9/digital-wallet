package com.ai.generated.tweet.to.kafka;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import com.ai.generated.tweet.to.kafka.init.StreamInitializer;
import com.ai.generated.tweet.to.kafka.runner.AIStreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@EnableScheduling
@ComponentScan(basePackages = "com.ai.generated.tweet.to.kafka")
public class AIGeneratedTweetToKafkaServiceApplication implements CommandLineRunner {
    private final Logger LOG = LoggerFactory.getLogger(AIGeneratedTweetToKafkaServiceApplication.class);

    private final AIGeneratedTweetToKafkaServiceConfigData configData;
    private final StreamInitializer streamInitializer;
    private final AIStreamRunner streamRunner;
    private final TaskScheduler taskScheduler;


    public static void main(String[] args) {
        SpringApplication.run(AIGeneratedTweetToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Application is starting...");
        LOG.info("Keywords: {}", configData.streamingDataKeywords());
        boolean initResult = streamInitializer.init();
        if (initResult) {
            log.info("Starting AI Stream Runner with fixed rate {} seconds!", configData.schedulerDurationSec());
            taskScheduler.scheduleAtFixedRate(streamRunner,
                    Duration.of(configData.schedulerDurationSec(), ChronoUnit.SECONDS));
        } else {
            log.error("Stream Initializer failed to initialize the streams! Not starting the AI Stream Runner!");
        }
    }

}