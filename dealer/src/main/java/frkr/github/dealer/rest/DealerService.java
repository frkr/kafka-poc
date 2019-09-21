package frkr.github.dealer.rest;

import frkr.github.ClienteRequest;
import frkr.github.DealerResponse;
import frkr.github.dealer.kafka.DealerStreamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class DealerService {

    @Autowired
    private DealerStreamsService dealerStreamsService;

    @RequestMapping(value = "deal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public DealerResponse deal(@RequestBody ClienteRequest clienteRequest) {
        dealerStreamsService.send(clienteRequest);
        return DealerResponse.builder()
                .id(300L)
                .build();
    }

}
