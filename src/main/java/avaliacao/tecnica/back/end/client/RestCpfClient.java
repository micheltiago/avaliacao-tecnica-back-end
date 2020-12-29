package avaliacao.tecnica.back.end.client;

import avaliacao.tecnica.back.end.dto.RetornoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RestCpfClient", url = "${service.url}")
public interface RestCpfClient {

    @GetMapping(value = "/users/{cpf}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RetornoDto getValidaCpf(@PathVariable String cpf);

}
