package io.intelliflow;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.reactive.messaging.annotations.Merge;
import org.eclipse.microprofile.reactive.messaging.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Random;
import java.util.stream.Stream;

@ApplicationScoped
public class MessagingApp {

    @Inject
    @Channel("words-out")
    Emitter<String> emitter;

    private final Random random = new Random();

    /**
     * Sends message to the "words-out" channel, can be used from a JAX-RS resource or any bean of your application.
     * Messages are sent to the broker.
     **/
    void onStart(@Observes StartupEvent ev) {
        Stream.of("Hello", "with", "SmallRye", "reactive", "message").forEach(string -> emitter.send(string));
    }

    /**
     * Consume the message from the "words-in" channel, uppercase it and send it to the uppercase channel.
     * Messages come from the broker.
     **/
    @Incoming("words-in")
    @Outgoing("uppercase")
    public Message<String> toUpperCase(Message<String> message) {
        return message.withPayload(message.getPayload().toUpperCase());
    }

    /**
     * Consume the uppercase channel (in-memory) and print the messages.
     **/
    @Incoming("uppercase")
    @Merge
    public void sink(String word) {
        System.out.println(">> " + word.toUpperCase());
    }

    /*@Incoming("prices-out")
    public void sink(Double word) {
        System.out.println(">> Number " + word);
    }

    @Outgoing("prices-out")
    public Double generate() {
        // Build an infinite stream of random prices
        // It emits a price every second
        //return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
               // .map(x -> random.nextDouble());
        Double d = 0.3;
        return d;
    }*/
}

