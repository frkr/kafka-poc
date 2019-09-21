package frkr.github.dealer.kafka;

import frkr.github.ClienteRequest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@EnableBinding(DealerStreams.class)
public class StreamsConfig {

    @StreamListener(DealerStreams.INPUT)
    public void handle(@Payload ClienteRequest request) {
        System.out.println(
                request
        );
        request.setId(42l);
    }

//    @StreamListener
//    public void process(@Input("input") KStream<String, PlayEvent> playEvents,
//                        @Input("inputX") KTable<Long, Song> songTable) {
//...
//    }

}
