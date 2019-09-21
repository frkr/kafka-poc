package frkr.github.emprestimos.rest;

import frkr.github.ClienteRequest;
import frkr.github.OfertaCCResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class OfertaCCService {

    @RequestMapping(value = "ofertacc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OfertaCCResponse ofertacc(@RequestBody ClienteRequest clienteRequest) {
        return OfertaCCResponse.builder()
                .valor(42.0)
                .promocao("POC Oferta CC")
                .build();
    }

}
