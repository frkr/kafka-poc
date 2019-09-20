package frkr.github.emprestimos.rest;

import frkr.github.ClienteRequest;
import frkr.github.OfertaCCResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@RestController
@RequestMapping("rs")
@WebService
@CrossOrigin
public class OfertaCCService {

    @WebMethod
    @WebResult(name = "OfertaCCResponse")
    @RequestMapping(value = "oferta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OfertaCCResponse oferta(@WebParam(name = "ClienteRequest") @RequestBody ClienteRequest clienteRequest) {
        return OfertaCCResponse.builder()
                .valor(42.0)
                .promocao("POC Oferta CC")
                .build();
    }

}
