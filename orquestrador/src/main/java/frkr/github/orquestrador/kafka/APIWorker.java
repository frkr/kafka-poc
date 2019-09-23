package frkr.github.orquestrador.kafka;

import frkr.github.kafka.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class APIWorker {

    @KafkaListener(topics = "${app.kafka.topic.hintin}")
    @SendTo("${app.kafka.topic.hintout}")
    public OrquestradorResponse hintservice(ClienteRequest request) throws Exception {
        return new OrquestradorResponse().add(
                new RestTemplate().postForEntity("http://localhost:8484/rs/hintservice", request, HintServiceResponse.class).getBody()
        );
    }

    @KafkaListener(topics = "${app.kafka.topic.ccin}")
    @SendTo("${app.kafka.topic.ccout}")
    public OrquestradorResponse ofertacc(ClienteRequest request) throws Exception {
        return new OrquestradorResponse().add(
                new RestTemplate().postForEntity("http://localhost:8181/rs/ofertacc", request, OfertaCCResponse.class).getBody()
        );
    }

    @KafkaListener(topics = "${app.kafka.topic.segvidain}")
    @SendTo("${app.kafka.topic.segvidaout}")
    public OrquestradorResponse segurovida(ClienteRequest request) throws Exception {
        return new OrquestradorResponse().add(
                new RestTemplate().postForEntity("http://localhost:8282/rs/segurovida", request, SeguroVidaResponse.class).getBody()
        );
    }

    @KafkaListener(topics = "${app.kafka.topic.empresin}")
    @SendTo("${app.kafka.topic.empresout}")
    public OrquestradorResponse emprestimos(ClienteRequest request) throws Exception {
        return new OrquestradorResponse().add(
                new RestTemplate().postForEntity("http://localhost:8383/rs/emprestimos", request, EmprestimosResponse.class).getBody()
        );
    }

}
