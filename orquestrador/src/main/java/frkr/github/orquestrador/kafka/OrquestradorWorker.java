package frkr.github.orquestrador.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.HintServiceResponse;
import frkr.github.kafka.OrquestradorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OrquestradorWorker {

    //region Topics
    @Value("${app.kafka.topic.hintin}")
    private String hintin;
    @Value("${app.kafka.topic.hintout}")
    private String hintout;
    @Value("${app.kafka.topic.ccin}")
    private String ccin;
    @Value("${app.kafka.topic.ccout}")
    private String ccout;
    @Value("${app.kafka.topic.segvidain}")
    private String segvidain;
    @Value("${app.kafka.topic.segvidaout}")
    private String segvidaout;
    @Value("${app.kafka.topic.empresin}")
    private String empresin;
    @Value("${app.kafka.topic.empresout}")
    private String empresout;
    //endregion

    @Autowired
    private ReplyingKafkaTemplate<String, ClienteRequest, OrquestradorResponse> hint;

    @KafkaListener(topics = "${app.kafka.topic.request}")
    @SendTo("${app.kafka.topic.response}")
    public OrquestradorResponse handle(ClienteRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Consultar o Hint Service

        OrquestradorResponse hintResponse = Producer.send(hint, hintin, hintout, request).get(30, TimeUnit.SECONDS).value();
        Object teste = hintResponse.getRetornos().get(0);

        HintServiceResponse teste2 = mapper.convertValue(teste, HintServiceResponse.class);
        System.out.println(
                teste2
        );

        return new OrquestradorResponse().add(new ClienteRequest(3L));// FIXME
    }

}
