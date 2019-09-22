package frkr.github.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrquestradorResponse {

    private List<Object> retornos = new LinkedList<>();

    public OrquestradorResponse add(Object o) {
        retornos.add(o);
        return this;
    }

}
