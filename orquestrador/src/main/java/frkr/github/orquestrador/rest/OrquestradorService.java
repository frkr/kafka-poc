package frkr.github.orquestrador.rest;

import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.OrquestradorResponse;
import frkr.github.orquestrador.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class OrquestradorService {

    @Autowired
    private ReplyingKafkaTemplate<String, ClienteRequest, OrquestradorResponse> kafka;

    @Value("${app.kafka.topic.request}")
    private String request;

    @Value("${app.kafka.topic.response}")
    private String response;

    @RequestMapping(value = "orquestrador", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrquestradorResponse orquestrador(@RequestBody ClienteRequest clienteRequest) throws Exception {
        return Producer.send(kafka, request, response, clienteRequest).get(30, TimeUnit.SECONDS).value();
    }

}
