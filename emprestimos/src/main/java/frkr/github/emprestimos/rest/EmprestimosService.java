package frkr.github.emprestimos.rest;

import frkr.github.ClienteRequest;
import frkr.github.EmprestimosResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class EmprestimosService {

    @RequestMapping(value = "emprestimos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmprestimosResponse emprestimos(@RequestBody ClienteRequest clienteRequest) {
        return EmprestimosResponse.builder()
                .valor(3000.0)
                .categoria("POC Emprestimos")
                .build();
    }

}
