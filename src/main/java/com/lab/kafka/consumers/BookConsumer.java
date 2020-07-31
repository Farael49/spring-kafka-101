package com.lab.kafka.consumers;

import com.lab.kafka.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

@Profile("consumer-book")
@Service
public class BookConsumer {

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @KafkaListener(id = "book-consumer", topics = "book-message")
    public void consumeMessage(Book book) {
        System.out.println("Got message: " + book);
    }
}
