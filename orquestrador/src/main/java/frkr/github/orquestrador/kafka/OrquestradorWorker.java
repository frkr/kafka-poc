package frkr.github.orquestrador.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.HintServiceResponse;
import frkr.github.kafka.OrquestradorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
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
    private ReplyingKafkaTemplate<String, ClienteRequest, OrquestradorResponse> kafka;

    @KafkaListener(topics = "${app.kafka.topic.request}")
    @SendTo("${app.kafka.topic.response}")
    public OrquestradorResponse handle(ClienteRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OrquestradorResponse retorno = new OrquestradorResponse();

        // Consultar o Hint Service

        HintServiceResponse hint = mapper.convertValue(
                Producer.send(kafka, hintin, hintout, request).get(30, TimeUnit.SECONDS).value().getRetornos().get(0)
                , HintServiceResponse.class);

        // Consultar backend API em forma paralelizada

        RequestReplyFuture<String, ClienteRequest, OrquestradorResponse> ccRequest = null;
        RequestReplyFuture<String, ClienteRequest, OrquestradorResponse> segRequest = null;
        RequestReplyFuture<String, ClienteRequest, OrquestradorResponse> empresRequest = null;
        for (Integer i : hint.getRetornos()) {
            if (i == 1) {
                ccRequest = Producer.send(kafka, ccin, ccout, request);
            } else if (i == 2) {
                segRequest = Producer.send(kafka, segvidain, segvidaout, request);
            } else if (i == 3) {
                empresRequest = Producer.send(kafka, empresin, empresout, request);
            }
        }

        // Agregar resultados

        for (Integer i : hint.getRetornos()) {
            if (i == 1) {
                retorno.add(ccRequest.get(30, TimeUnit.SECONDS).value().getRetornos().get(0));
            } else if (i == 2) {
                retorno.add(segRequest.get(30, TimeUnit.SECONDS).value().getRetornos().get(0));
            } else if (i == 3) {
                retorno.add(empresRequest.get(30, TimeUnit.SECONDS).value().getRetornos().get(0));
            }
        }

        return retorno;
    }

}
