package avaliacao.tecnica.back.end.util;

import avaliacao.tecnica.back.end.domain.Pauta;
import avaliacao.tecnica.back.end.domain.Votacao;
import avaliacao.tecnica.back.end.dto.CadastroPautaDto;
import avaliacao.tecnica.back.end.dto.RetornoServicoDto;
import java.time.LocalDateTime;
import java.util.List;

public class Constants {

    public static Pauta pauta() {
        return Pauta
                .builder()
                .id("173245")
                .dataSessao(LocalDateTime.now())
                .build();
    }

    public static List<CadastroPautaDto> criarPauta(String id) {
        return List.of(cadastroPautaDto());
    }

    public static CadastroPautaDto cadastroPautaDto() {
        return CadastroPautaDto
                .builder()
                .idPauta(pauta().getId())
                .totalVotos(0L)
                .build();
    }

    public static Votacao votacao(Pauta pauta) {
        return Votacao
                .builder()
                .cpf("97232041001")
                .pauta(pauta)
                .voto("S")
                .build();
    }

    public static RetornoServicoDto cpfInvalido() {
        return RetornoServicoDto
                .builder()
                .status("UNABLE_TO_VOTE")
                .build();
    }

    public static RetornoServicoDto cpfvalido() {
        return RetornoServicoDto
                .builder()
                .status("ABLE_TO_VOTE")
                .build();
    }

}
