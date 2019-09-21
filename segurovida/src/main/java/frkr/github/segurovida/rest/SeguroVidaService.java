package frkr.github.segurovida.rest;

import frkr.github.ClienteRequest;
import frkr.github.SeguroVidaResponse;
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
