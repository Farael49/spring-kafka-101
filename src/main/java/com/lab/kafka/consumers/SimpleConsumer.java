package com.lab.kafka.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("simple-consumer")
@Service
public class SimpleConsumer {
    @KafkaListener(id = "simple-consumer", topics = "simple-topic")
    public void consumeMessage(String message) {
        System.out.println("[SIMPLE-CONSUMER] Got message: " + message);
    }
}
