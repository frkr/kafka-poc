package frkr.github.emprestimos.rest;

import frkr.github.ClienteRequest;
import frkr.github.EmprestimosResponse;
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
public class EmprestimosService {

    @WebMethod
    @WebResult(name = "EmprestimosResponse")
    @RequestMapping(value = "oferta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmprestimosResponse oferta(@WebParam(name = "ClienteRequest") @RequestBody ClienteRequest clienteRequest) {
        return EmprestimosResponse.builder()
                .valor(3000.0)
                .categoria("POC Emprestimos")
                .build();
    }

}
