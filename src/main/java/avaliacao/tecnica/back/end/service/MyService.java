package avaliacao.tecnica.back.end.service;

import avaliacao.tecnica.back.end.client.RestCpfClient;
import avaliacao.tecnica.back.end.domain.Pauta;
import avaliacao.tecnica.back.end.domain.Votacao;
import avaliacao.tecnica.back.end.dto.CadastroPautaDto;
import avaliacao.tecnica.back.end.dto.RetornoServicoDto;
import avaliacao.tecnica.back.end.exception.AvaliacaoException;
import avaliacao.tecnica.back.end.producer.AvaliacaoProducer;
import avaliacao.tecnica.back.end.repository.PautaRepository;
import avaliacao.tecnica.back.end.repository.VotacaoRepository;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.time.LocalDateTime;
import java.util.List;
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
public class MyService {

    private final RestCpfClient client;
    private final PautaRepository pautaRepository;
    private final VotacaoRepository votacaoRepository;
    private final AvaliacaoProducer producer;

    public List<CadastroPautaDto> cadastrarPauta(Integer tempo) {
        log.info("cadastrarPauta...");

        Pauta p = this.pautaRepository.save(Pauta
                .builder()
                .id(String.valueOf(LocalDateTime.now().getNano()))
                .dataSessao(LocalDateTime.now())
                .tempo(tempo)
                .build());
        Map<String, Long> votos = this.votacaoRepository.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy((Votacao v) -> v.getPauta().getId(), Collectors.counting())
                );
        votos.put(p.getId(), 0L);

        return votos.entrySet().stream().map(m -> new CadastroPautaDto(m.getKey(), m.getValue()))
                .collect(Collectors.toList());
    }

    public Votacao votar(String pauta, String cpf, String voto) {
        log.info("Votar...");
        if (validarPautaAberta(pauta)) {
            if (validarCPF(cpf)) {
                if (validarSeJaVotou(pauta, cpf)) {
                    Votacao votacao = this.votacaoRepository.save(Votacao
                            .builder()
                            .cpf(cpf)
                            .pauta(Pauta.builder().id(pauta).build())
                            .voto(voto)
                            .build());
                    this.producer.send(votacao);
                    return votacao;
                }
                throw new AvaliacaoException("USUARIO JA VOTOU!");
            }
            throw new AvaliacaoException("CPF INVALIDO!");
        }
        throw new AvaliacaoException("VOTACAO ENCERRADA!");
    }

    private boolean validarPautaAberta(String pauta) {
        Optional<Pauta> entity = this.pautaRepository.findById(pauta);
        if (entity.isPresent()) {
            LocalDateTime data = entity.get().getDataSessao().plusMinutes(Objects.isNull(entity.get().getTempo()) ? 1 : entity.get().getTempo());
            if (data.isAfter(LocalDateTime.now())) {
                return TRUE;
            }
        }
        return FALSE;
    }

    private boolean validarSeJaVotou(String idPauta, String cpf) {
        return this.votacaoRepository.findByCpfAndPauta(cpf, Pauta.builder()
                .id(idPauta)
                .build()
        ).isEmpty();
    }

    private boolean validarCPF(String cpf) {
        try {
            RetornoServicoDto ret = this.client.getValidaCpf(cpf);
            if (Objects.nonNull(ret) && ret.getStatus().equals("ABLE_TO_VOTE")) {
                return TRUE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return FALSE;
    }

}
