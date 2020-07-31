package com.lab.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("producer")
@Service
public class SimpleProducer {

    private final KafkaTemplate<String, String> simpleProducer;

    public SimpleProducer(KafkaTemplate<String, String> simpleProducer){
        this.simpleProducer = simpleProducer;
        produceMessages();
    }

    private void produceMessages() {
        new Thread(() -> {
            for(;;) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.send("Message " + UUID.randomUUID());
            }
        }).start();
    }

    public void send(String message) {
        System.out.println("Sending " + message);
        simpleProducer.send("simple-message", message);
    }
}