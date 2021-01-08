package avaliacao.tecnica.back.end.controller;

import avaliacao.tecnica.back.end.domain.Votacao;
import avaliacao.tecnica.back.end.dto.CadastroPautaDto;
import avaliacao.tecnica.back.end.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Api(value = "Avaliação Técnica BACK-END", tags = {"BACK-END"})
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class AvaliacaoController {

    @Autowired
    private final MyService service;

    @ApiOperation(value = "Cadastrar uma nova pauta.", response = String.class)
    @GetMapping(path = "/pauta/{tempo}")
    public ResponseEntity<List<CadastroPautaDto> > cadastrar(
            @RequestParam(name = "tempo", required = false) Integer tempo
    ) {
        log.info("Cadastrar uma nova pauta ");
        return ResponseEntity.ok(this.service.cadastrarPauta(tempo));
    }

    @ApiOperation(value = "Votar.", response = String.class)
    @GetMapping(path = "/votacao/{pauta}/{voto}/{cpf}")
    public ResponseEntity<Votacao> votar(
            @RequestParam(name = "cpf", required = true) String cpf,
            @RequestParam(name = "pauta", required = true) String pauta,
            @RequestParam(name = "voto", required = true) String voto) {

        log.info("Cpf:" + cpf + "Pauta:" + pauta + "Voto:" + voto);
        return ResponseEntity.ok(this.service.votar(pauta, cpf, voto));
    }

}
