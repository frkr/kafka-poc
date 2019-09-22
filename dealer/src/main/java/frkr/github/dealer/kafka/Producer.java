package frkr.github.dealer.kafka;

import frkr.github.ClienteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.UUID;

@Service
public class Producer {

    @Autowired
    private Topic topic;

    @Autowired
    private ProducerFactory pf;



    public void send(ClienteRequest clienteRequest) {
        ReplyingKafkaTemplate template = new ReplyingKafkaTemplate(pf,);
        topic.input().send(
                MessageBuilder
                        .withPayload(clienteRequest)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                        .build()
        );
        Message<?> teste = pool().receive();
        System.out.println(
                teste.getPayload()
        );
    }

}
