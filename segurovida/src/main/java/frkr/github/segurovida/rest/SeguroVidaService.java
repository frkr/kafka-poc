package frkr.github.segurovida.rest;

import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.SeguroVidaResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class SeguroVidaService {

    @RequestMapping(value = "segurovida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SeguroVidaResponse segurovida(@RequestBody ClienteRequest clienteRequest) {
        return SeguroVidaResponse.builder()
                .msg("POC Seguro Vida")
                .build();
    }

}
