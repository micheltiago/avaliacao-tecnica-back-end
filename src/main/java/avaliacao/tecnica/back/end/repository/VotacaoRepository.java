package avaliacao.tecnica.back.end.repository;

import avaliacao.tecnica.back.end.domain.Pauta;
import avaliacao.tecnica.back.end.domain.Votacao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

    public Optional<Votacao> findByCpfAndPauta(String cpf, Pauta pauta);

}
