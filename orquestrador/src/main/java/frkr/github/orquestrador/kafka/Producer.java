package frkr.github.orquestrador.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;

import java.nio.charset.Charset;

public final class Producer {

    private Producer() {
    }

    public static <V, R> RequestReplyFuture<String, V, R> send(ReplyingKafkaTemplate<String, V, R> kafkaTemplate, String request, String response, V value) {
        ProducerRecord<String, V> record = new ProducerRecord<String, V>(request, value);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, response.getBytes(Charset.forName("UTF-8"))));
        return kafkaTemplate.sendAndReceive(record);
    }
}
