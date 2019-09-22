package frkr.github.dealer.kafka;

import frkr.github.ClienteRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class Broker {

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public ClienteRequest handle(ClienteRequest request) {
        return new ClienteRequest(2L);
    }

}
