package frkr.github.dealer.kafka;

import frkr.github.ClienteRequest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Topic.class)
public class Broker {

    @StreamListener(Topic.INPUT)
    @SendTo(Topic.OUTPUT)
    public ClienteRequest handle(@Payload ClienteRequest request) {
        return new ClienteRequest(2L);
    }

//    @StreamListener(Topic.OUTPUT)
//    public void handle2(@Payload ClienteRequest request) {
//        System.out.println(
//                request
//        );
//    }

//    @StreamListener(DealerStreams.INPUT)
//    public void handle2(@Payload ClienteRequest request) {
//        System.out.println(
//                request
//        );
//        request.setId(42l);
//    }

//    @StreamListener(Topic.INPUT_STREAM)
//    @SendTo(Topic.OUTPUT_STREAM)
//    public KTable<String, ClienteRequest> process(KStream<String, ClienteRequest> request) {
//        return request.groupByKey().aggregate(
//                ClienteRequest::new
//                , (stationId, value, aggregation) -> new ClienteRequest(value.getId())
//                , Materialized.as("teste")
//        );
//    }

//    @StreamListener
//    public void process2(@Input(Topic.OUTPUT_STREAM) KStream<String, ClienteRequest> request) {
//        request.foreach((i, o) -> System.out.println(o));
//    }

}
