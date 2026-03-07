package com.ai.generated.tweet.to.kafka.init.impl;

import com.ai.generated.tweet.to.kafka.init.StreamInitializer;
import org.springframework.stereotype.Component;


@Component
public class KafkaStreamInitializer implements StreamInitializer {
    @Override
    public boolean init() {
        return true;
    }
}
