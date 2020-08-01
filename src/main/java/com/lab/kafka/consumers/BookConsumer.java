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

@Profile("book-consumer")
@Service
public class BookConsumer {

    /**
     * Converter to allow Objects in the consumer, mapped from JSON
     * Can also be configured with the application properties (see example in resources)
     */
    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    /**
     * Configure the "DLT" Dead Letter Topic
     * by default the resolver will add ".DLT" to the topic we had an exception with
     * For some reason, with maxAttempts=0 we don't see the exception's stracktrace but it properly enters the DLT on first issue
     * However a real world case should -probably- always use a maxAttempt > 0
     */
    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1_000, 0));
    }

    /**
     * A basic consumer, expecting a Book object, and randomly encountering an issue to showcase the DLT
     */
    @KafkaListener(id = "book-consumer", topics = "book-topic")
    public void consumeMessage(Book book) {
        boolean triggerRandomError = Utils.random(0, 10) == 0;
        if (triggerRandomError)
            throw new IllegalArgumentException("Fake exception to populate the DLQ " + book.toString());
        System.out.println("[BOOK-CONSUMER] Got message: " + book);
    }

    /**
     * A second consumer, this one is made to process the DLT
     */
    @KafkaListener(id = "dlt-consumer", topics = "book-topic.DLT")
    public void dltListener(String dl) {
        System.out.println("[DLT] Got message: " + dl);
    }
}
