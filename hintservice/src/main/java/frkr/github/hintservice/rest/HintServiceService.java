package frkr.github.hintservice.rest;

import frkr.github.kafka.ClienteRequest;
import frkr.github.kafka.HintServiceResponse;
import org.springframework.boot.actuate.integration.IntegrationGraphEndpoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("rs")
@CrossOrigin
public class HintServiceService {

    private static final Map<Integer,List<Integer>> hints = new HashMap<>();
    private static final AtomicInteger stack = new AtomicInteger(1);

    private static Integer next() {
        return stack.getAndUpdate(o -> {
            int rt = o + 1;
            if (rt > 3) {
                rt = 1;
            }
            return rt;
        });
    }

    static {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        hints.put(1, list);
        list = new ArrayList<>();
        list.add(3);
        list.add(2);
        hints.put(2, list);
        list = new ArrayList<>();
        list.add(2);
        list.add(1);
        hints.put(3, list);
    }

    @RequestMapping(value = "hintservice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HintServiceResponse hintservice(@RequestBody ClienteRequest clienteRequest) throws Exception {
        return HintServiceResponse.builder()
                .retornos(hints.get(next()))
                .build();
    }

}
