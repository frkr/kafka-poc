package frkr.github.dealer.kafka;

import frkr.github.ClienteRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Producer {

    @Autowired
    private ReplyingKafkaTemplate<String, ClienteRequest, ClienteRequest> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    public ClienteRequest send(ClienteRequest clienteRequest) throws Exception {
        // create producer record
        ProducerRecord<String, ClienteRequest> record = new ProducerRecord<String, ClienteRequest>(requestTopic, clienteRequest);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, ClienteRequest, ClienteRequest> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        // confirm if producer produced successfully
        //SendResult<String, ClienteRequest> sendResult = sendAndReceive.getSendFuture().get();

        //print all headers
        //sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        // get consumer record
        ConsumerRecord<String, ClienteRequest> consumerRecord = sendAndReceive.get(30, TimeUnit.SECONDS);
        // return consumer value
        return consumerRecord.value();
    }

}
