package avaliacao.tecnica.back.end.service;

import avaliacao.tecnica.back.end.client.RestCpfClient;
import avaliacao.tecnica.back.end.domain.Pauta;
import avaliacao.tecnica.back.end.domain.Votacao;
import avaliacao.tecnica.back.end.dto.RetornoDto;
import avaliacao.tecnica.back.end.exception.AvaliacaoException;
import avaliacao.tecnica.back.end.producer.AvaliacaoProducer;
import avaliacao.tecnica.back.end.repository.PautaRepository;
import avaliacao.tecnica.back.end.repository.VotacaoRepository;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceBackEnd {

    private final RestCpfClient client;
    private final PautaRepository pauta;
    private final VotacaoRepository votacao;
    private final AvaliacaoProducer producer;

    public String cadastrarPauta(Integer tempo) {
        log.info("cadastrarPauta...");

        Pauta p = this.pauta.save(Pauta
                .builder()
                .id(String.valueOf(LocalDateTime.now().getNano()))
                .dataSessao(LocalDateTime.now())
                .tempo(tempo)
                .build());
        Map<String, Long> votos = this.votacao.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy((Votacao v) -> v.getPauta().getId(), Collectors.counting())
                );
        votos.put(p.getId(), 0L);
        return votos.toString();
    }

    public String votar(String pauta, String cpf, String voto) {
        log.info("Votar...");
        if (validarPautaAberta(pauta)) {
            if (validarCPF(cpf)) {
                if (validarSeJaVotou(pauta, cpf)) {
                    Votacao v = this.votacao.save(Votacao
                            .builder()
                            .cpf(cpf)
                            .pauta(Pauta.builder().id(pauta).build())
                            .voto(voto)
                            .build());
                    this.producer.send(v);
                    return v.toString();
                }
                throw new AvaliacaoException("USUARIO JA VOTOU!");
            }
            throw new AvaliacaoException("CPF INVALIDO!");
        }
        throw new AvaliacaoException("VOTACAO ENCERRADA!");
    }

    private boolean validarPautaAberta(String p) {
        Optional<Pauta> entity = this.pauta.findById(p);
        if (entity.isPresent()) {
            LocalDateTime data = entity.get().getDataSessao().plusMinutes(Objects.isNull(entity.get().getTempo()) ? 1 : entity.get().getTempo());
            if (data.isAfter(LocalDateTime.now())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private boolean validarSeJaVotou(String P, String cpf) {
        Optional<Votacao> entity = this.votacao.findByCpfAndPauta(cpf, Pauta.builder()
                .id(P)
                .build()
        );
        if (entity.isPresent()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean validarCPF(String cpf) {
        try {
            RetornoDto ret = this.client.getValidaCpf(cpf);
            if (Objects.nonNull(ret) && ret.getStatus().equals("ABLE_TO_VOTE")) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }

}
