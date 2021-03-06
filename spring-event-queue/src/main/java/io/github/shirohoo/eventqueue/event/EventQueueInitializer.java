package io.github.shirohoo.eventqueue.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueInitializer {
    @Bean
    public TransactionEventQueue transactionEventQueue() {
        return TransactionEventQueue.of(1_000);
    }
}
