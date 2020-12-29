package avaliacao.tecnica.back.end.util;

import avaliacao.tecnica.back.end.domain.Pauta;
import avaliacao.tecnica.back.end.domain.Votacao;
import avaliacao.tecnica.back.end.dto.RetornoDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static Pauta pauta() {
        return Pauta
                .builder()
                .id(String.valueOf(LocalDateTime.now().getNano()))
                .dataSessao(LocalDateTime.now())
                .build();
    }

    public static String criarPauta(String id) {
        Map<String, Long> out = new HashMap<>();
        out.put(id, 0L);
        return out.toString();
    }

    public static Votacao votacao(Pauta pauta) {
        return Votacao
                .builder()
                .cpf("97232041001")
                .pauta(pauta)
                .voto("S")
                .build();
    }

    public static RetornoDto cpfInvalido() {
        return RetornoDto
                .builder()
                .status("UNABLE_TO_VOTE")
                .build();
    }

    public static RetornoDto cpfvalido() {
        return RetornoDto
                .builder()
                .status("ABLE_TO_VOTE")
                .build();
    }

}
