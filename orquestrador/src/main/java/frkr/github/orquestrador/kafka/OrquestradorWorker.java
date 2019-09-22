package frkr.github.orquestrador.kafka;

import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.HintServiceResponse;
import frkr.github.kafka.OrquestradorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrquestradorWorker {

    //region Hint Service
    @Autowired
    private ReplyingKafkaTemplate<String, ClienteRequest, HintServiceResponse> hint;

    @Value("${app.kafka.topic.hintin}")
    private String hintin;

    @Value("${app.kafka.topic.hintout}")
    private String hintout;
    //endregion

    @KafkaListener(topics = "${app.kafka.topic.request}", containerFactory = "kafkaListenerContainerFactoryOrquestrador")
    @SendTo
    public OrquestradorResponse handle(ClienteRequest request) {
        return new OrquestradorResponse().add(new ClienteRequest(2L));
    }

    //region Hint Worker
    @KafkaListener(topics = "${app.kafka.topic.hintin}", containerFactory = "kafkaListenerContainerFactoryHint")
    @SendTo
    public HintServiceResponse workerHint(ClienteRequest request) {
        return new RestTemplate().postForEntity("http://localhost:8484", request, HintServiceResponse.class).getBody();
    }
    //endregion

}
