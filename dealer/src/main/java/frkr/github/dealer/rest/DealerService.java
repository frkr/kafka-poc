package frkr.github.dealer.rest;

import frkr.github.ClienteRequest;
import frkr.github.DealerResponse;
import frkr.github.dealer.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class DealerService {

    @Autowired
    private Producer producer;

    @RequestMapping(value = "deal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public DealerResponse deal(@RequestBody ClienteRequest clienteRequest) throws Exception {
        System.out.println(
                producer.send(clienteRequest)
        );
        return DealerResponse.builder()
                .id(300L)
                .build();
    }

}
