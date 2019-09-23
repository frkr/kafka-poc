package frkr.github.orquestrador.kafka;

import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.OrquestradorResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    //region SendTo Topics
    @Value("${app.kafka.topic.response}")
    private String response;
    @Value("${app.kafka.topic.hintout}")
    private String hintout;
    @Value("${app.kafka.topic.ccout}")
    private String ccout;
    @Value("${app.kafka.topic.segvidaout}")
    private String segvidaout;
    @Value("${app.kafka.topic.empresout}")
    private String empresout;
    //endregion

    //region Kafka Props
    @Value("${app.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> kafkaProps() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Value("${app.kafka.consumergroup.requestresponse}")
    private String groupid;

    public Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupid);
        return props;
    }
    //endregion

    //region Produtores
    @Bean
    public ProducerFactory<String, ClienteRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProps());
    }

    @Bean
    public KafkaTemplate<String, ClienteRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    //endregion

    //region KAFKA LISTENERS
    @Bean
    public ReplyingKafkaTemplate<String, ClienteRequest, OrquestradorResponse> replyKafkaTemplate(ProducerFactory<String, ClienteRequest> pf, KafkaMessageListenerContainer<String, OrquestradorResponse> container) {
        return new ReplyingKafkaTemplate<>(pf, container);
    }

    @Bean
    public KafkaMessageListenerContainer<String, OrquestradorResponse> replyContainer(ConsumerFactory<String, OrquestradorResponse> cf) {
        ContainerProperties containerProperties = new ContainerProperties(response,hintout,ccout,segvidaout,empresout);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }

    @Bean
    public ConsumerFactory<String, OrquestradorResponse> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), new JsonDeserializer<>(OrquestradorResponse.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrquestradorResponse>> kafkaListenerContainerFactory(ConsumerFactory<String, OrquestradorResponse> cf) {
        ConcurrentKafkaListenerContainerFactory<String, OrquestradorResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    //endregion

    //region Filas Descr
    public static void main(String[] args) {
        final String postfix = ":1:1:delete";

        List<String> lista = new LinkedList<>();

        lista.add("request" + postfix);
        lista.add("response" + postfix);
        lista.add("hintin" + postfix);
        lista.add("hintout" + postfix);
        lista.add("ccin" + postfix);
        lista.add("ccout" + postfix);
        lista.add("segvidain" + postfix);
        lista.add("segvidaout" + postfix);
        lista.add("empresin" + postfix);
        lista.add("empresout" + postfix);

        System.out.println(
                String.join(",", lista)
        );
    }
    //endregion

}

