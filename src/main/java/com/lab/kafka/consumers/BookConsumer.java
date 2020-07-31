package com.lab.kafka.consumers;

import com.lab.kafka.Book;
import com.lab.kafka.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.backoff.FixedBackOff;

@Profile("consumer-book")
@Service
public class BookConsumer {

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 1));
    }

    @KafkaListener(id = "book-consumer", topics = "book-message")
    public void consumeMessage(Book book) {
        if (Utils.random(0, 10) == 0)
            throw new IllegalArgumentException("Fake exception to populate the DLQ " + book.toString());
        System.out.println("Got message: " + book);
    }
}
