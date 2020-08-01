# spring-kafka-101

Basic examples using spring &amp; kafka and common producer/consumer use cases

The application has multiple profiles to start the parts we want. 

We can run separately the consumers/producers instances as a proper use case would reflect (& in separate codebases 🙄), or run them all within the same app for ease of testing.

# Simple producer/consumer use case
The SimpleProducer and SimpleConsumer classes are basic examples of sending and receiving String messages
Profiles:
- simple-producer: sends a message to simple-topic as a String
- simple-consumer: reads a message from simple-topic as a String

# Producer/Consumer with JSON and error handling (Dead Letter Queue/Topic)
The BookProducer and  BookConsumer classes send / receive json objects to / from Kafka.
The consumer also randomly throws an Exception, to showcase the Dead Letter Queue (or Dead Letter Topic, DLQ/DLT) configured through the SeekToCurrentErrorHandler bean.


The json handling is quite straightforward, and we could easily switch from Json serialization to another format. The actual serialization/deserialization is left to Spring (through the jackson lib), but could be done manually.

The DLQ pattern example is kept simple, when encountering an error trying to consume a message, and after X attempts (configurable, after X time, Fixed or Exponential) we send the message to another topic.

This is made to prevent from blocking our consumers constantly trying to consume the same message we keep having an exception with. We can then handle these separately, whether we need to process these again (in case of an external/temporary failure from a service) or have to handle them differently (change our code to support a new use case). 

Profiles:
- book-producer: sends a message to book-topic as a Book Object (with json serialization)
- book-consumer: reads a message from book-topic as a Book (with Json deserialization), and randomly fails to showcase the DLQ pattern.  Contains a second consumer to show the messages arriving in the DLQ.

    
#Sources:
- https://www.baeldung.com/spring-kafka
- https://github.com/spring-projects/spring-kafka/blob/master/samples/
- https://dev.to/thegroo/spring-kafka-producer-and-consumer-41oc