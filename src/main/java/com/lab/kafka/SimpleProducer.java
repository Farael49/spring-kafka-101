package com.lab.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Profile("producer")
@Service
public class SimpleProducer {

    private final KafkaTemplate<String, String> simpleProducer;

    public SimpleProducer(KafkaTemplate<String, String> simpleProducer) {
        this.simpleProducer = simpleProducer;
        for (int i = 0; i < 10; i++)
            this.send("Message " + UUID.randomUUID());
    }

    public void send(String message) {
        System.out.println("Sending " + message);
        simpleProducer.send("simple-message", message);
    }
}