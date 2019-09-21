package frkr.github.dealer.kafka;

import frkr.github.ClienteRequest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@EnableBinding(DealerStreams.class)
public class DealerStreamsService {

    private DealerStreams dealerStreams;
    public DealerStreamsService(DealerStreams dealerStreams) {
        this.dealerStreams = dealerStreams;
    }

    public void send(ClienteRequest clienteRequest) {
        MessageChannel messageChannel = dealerStreams.output();
        messageChannel.send(MessageBuilder
                .withPayload(clienteRequest)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }

}
