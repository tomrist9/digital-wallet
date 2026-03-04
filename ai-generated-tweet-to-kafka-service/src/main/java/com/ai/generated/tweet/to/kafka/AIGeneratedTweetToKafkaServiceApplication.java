package com.ai.generated.tweet.to.kafka;

import com.ai.generated.tweet.to.kafka.config.AIGeneratedTweetToKafkaServiceConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AIGeneratedTweetToKafkaServiceApplication implements CommandLineRunner {
    private final Logger LOG = LoggerFactory.getLogger(AIGeneratedTweetToKafkaServiceApplication.class);

    private  final AIGeneratedTweetToKafkaServiceConfigData configData;

    public AIGeneratedTweetToKafkaServiceApplication(AIGeneratedTweetToKafkaServiceConfigData configData) {
        this.configData = configData;
    }

    public static void main(String[] args) {
        SpringApplication.run(AIGeneratedTweetToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Application is starting...");
        LOG.info("Keywords: {}", configData.streamingDataKeywords());
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
