package frkr.github.dealer.kafka;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;

// FIXME deadcode
public interface Topic {

    String INPUT = "request";
    String OUTPUT = "response";
//    String INPUT_STREAM = "mytopic-in-stream";
//    String OUTPUT_STREAM = "mytopic-out-stream";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    SubscribableChannel output();

//    SubscribableChannel
//    MessageChannel output();

//    @Input(INPUT_STREAM)
//    KStream<?, ?> inputStream();
//
//    @Output(OUTPUT_STREAM)
//    KStream<?, ?> outputStream();

}
