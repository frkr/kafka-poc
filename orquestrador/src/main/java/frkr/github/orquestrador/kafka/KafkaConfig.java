package frkr.github.orquestrador.kafka;

import frkr.github.kafka.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
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

    //region Kafka Props
    @Value("${app.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> kafkaProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    //endregion

    //region Orquestrador Props
    @Value("${app.kafka.topic.response}")
    private String response;
    @Value("${app.kafka.consumergroup.requestresponse}")
    private String group_requestresponse;
    //endregion

    //region Hint Props
    @Value("${app.kafka.topic.hintout}")
    private String hintout;
    @Value("${app.kafka.consumergroup.hint}")
    private String group_hint;
    //endregion

    //region CC Props
    @Value("${app.kafka.topic.ccout}")
    private String ccout;
    @Value("${app.kafka.consumergroup.cc}")
    private String group_cc;
    //endregion

    //region Seguro Vida Props
    @Value("${app.kafka.topic.segvidaout}")
    private String segvidaout;
    @Value("${app.kafka.consumergroup.segvida}")
    private String group_segvida;
    //endregion

    //region Emprestimos Props
    @Value("${app.kafka.topic.empresout}")
    private String empresout;
    @Value("${app.kafka.consumergroup.empres}")
    private String group_empres;
    //endregion

    //region Producers
    @Bean
    public ProducerFactory<String, ClienteRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProps());
    }

    @Bean
    public KafkaTemplate<String, ClienteRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    //endregion

    //region Client Orquestrador
    public Map<String, Object> orquestradorProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_requestresponse);
        return props;
    }

    @Bean
    public ReplyingKafkaTemplate<String, ClienteRequest, OrquestradorResponse> replyKafkaTemplateOrquestrador() {
        return new ReplyingKafkaTemplate<String, ClienteRequest, OrquestradorResponse>(producerFactory(), replyContainerOrquestrador());
    }

    public KafkaMessageListenerContainer<String, OrquestradorResponse> replyContainerOrquestrador() {
        ContainerProperties containerProperties = new ContainerProperties(response);
        return new KafkaMessageListenerContainer<>(consumerFactoryOrquestrador(), containerProperties);
    }

    @Bean("consumerFactoryOrquestrador")
    public ConsumerFactory<String, OrquestradorResponse> consumerFactoryOrquestrador() {
        return new DefaultKafkaConsumerFactory<>(orquestradorProps(), new StringDeserializer(), new JsonDeserializer<>(OrquestradorResponse.class));
    }

    @Bean("kafkaListenerContainerFactoryOrquestrador")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrquestradorResponse>> kafkaListenerContainerFactoryOrquestrador() {
        ConcurrentKafkaListenerContainerFactory<String, OrquestradorResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOrquestrador());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    //endregion

    //region Client Hint
    public Map<String, Object> hintProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_hint);
        return props;
    }

    @Bean
    public ReplyingKafkaTemplate<String, ClienteRequest, HintServiceResponse> replyKafkaTemplateHint() {
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainerHint());
    }

    public KafkaMessageListenerContainer<String, HintServiceResponse> replyContainerHint() {
        ContainerProperties containerProperties = new ContainerProperties(hintout);
        return new KafkaMessageListenerContainer<>(consumerFactoryHint(), containerProperties);
    }

    @Bean("consumerFactoryHint")
    public ConsumerFactory<String, HintServiceResponse> consumerFactoryHint() {
        return new DefaultKafkaConsumerFactory<>(hintProps(), new StringDeserializer(), new JsonDeserializer<>(HintServiceResponse.class));
    }

    @Bean("kafkaListenerContainerFactoryHint")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, HintServiceResponse>> kafkaListenerContainerFactoryHint() {
        ConcurrentKafkaListenerContainerFactory<String, HintServiceResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryHint());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    //endregion

    //region Client CC
    public Map<String, Object> ccProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_requestresponse);
        return props;
    }

    @Bean
    public ReplyingKafkaTemplate<String, ClienteRequest, OfertaCCResponse> replyKafkaTemplateCC() {
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainerCC());
    }

    public KafkaMessageListenerContainer<String, OfertaCCResponse> replyContainerCC() {
        ContainerProperties containerProperties = new ContainerProperties(ccout);
        return new KafkaMessageListenerContainer<>(consumerFactoryCC(), containerProperties);
    }

    @Bean("consumerFactoryCC")
    public ConsumerFactory<String, OfertaCCResponse> consumerFactoryCC() {
        return new DefaultKafkaConsumerFactory<>(ccProps(), new StringDeserializer(), new JsonDeserializer<>(OfertaCCResponse.class));
    }

    @Bean("kafkaListenerContainerFactoryCC")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OfertaCCResponse>> kafkaListenerContainerFactoryCC() {
        ConcurrentKafkaListenerContainerFactory<String, OfertaCCResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryCC());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    //endregion

    //region Client Seguro Vida
    public Map<String, Object> segvidaProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_requestresponse);
        return props;
    }

    @Bean
    public ReplyingKafkaTemplate<String, ClienteRequest, SeguroVidaResponse> replyKafkaTemplateSegVida() {
        return new ReplyingKafkaTemplate<String, ClienteRequest, SeguroVidaResponse>(producerFactory(), replyContainerSegVida());
    }

    public KafkaMessageListenerContainer<String, SeguroVidaResponse> replyContainerSegVida() {
        ContainerProperties containerProperties = new ContainerProperties(segvidaout);
        return new KafkaMessageListenerContainer<>(consumerFactorySegVida(), containerProperties);
    }

    @Bean("consumerFactorySegVida")
    public ConsumerFactory<String, SeguroVidaResponse> consumerFactorySegVida() {
        return new DefaultKafkaConsumerFactory<>(segvidaProps(), new StringDeserializer(), new JsonDeserializer<>(SeguroVidaResponse.class));
    }

    @Bean("kafkaListenerContainerFactorySegVida")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SeguroVidaResponse>> kafkaListenerContainerFactorySegVida() {
        ConcurrentKafkaListenerContainerFactory<String, SeguroVidaResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactorySegVida());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
    //endregion

    //region Client Emprestimo
    public Map<String, Object> emprestimoProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_requestresponse);
        return props;
    }

    @Bean
    public ReplyingKafkaTemplate<String, ClienteRequest, EmprestimosResponse> replyKafkaTemplateEmprestimo() {
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainerEmprestimo());
    }

    public KafkaMessageListenerContainer<String, EmprestimosResponse> replyContainerEmprestimo() {
        ContainerProperties containerProperties = new ContainerProperties(empresout);
        return new KafkaMessageListenerContainer<>(consumerFactoryEmprestimos(), containerProperties);
    }

    @Bean("consumerFactoryEmprestimo")
    public ConsumerFactory<String, EmprestimosResponse> consumerFactoryEmprestimos() {
        return new DefaultKafkaConsumerFactory<>(emprestimoProps(), new StringDeserializer(), new JsonDeserializer<>(EmprestimosResponse.class));
    }

    @Bean("kafkaListenerContainerFactoryEmprestimo")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmprestimosResponse>> kafkaListenerContainerFactoryEmprestimo() {
        ConcurrentKafkaListenerContainerFactory<String, EmprestimosResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryEmprestimos());
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

