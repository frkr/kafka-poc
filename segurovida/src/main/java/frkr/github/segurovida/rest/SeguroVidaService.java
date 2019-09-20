package frkr.github.segurovida.rest;

import frkr.github.ClienteRequest;
import frkr.github.SeguroVidaResponse;
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
public class SeguroVidaService {

    @WebMethod
    @WebResult(name = "SeguroVidaResponse")
    @RequestMapping(value = "segurovida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SeguroVidaResponse segurovida(@WebParam(name = "ClienteRequest") @RequestBody ClienteRequest clienteRequest) {
        return SeguroVidaResponse.builder()
                .msg("POC Seguro Vida")
                .build();
    }

}
