package avaliacao.tecnica.back.end.service;

import avaliacao.tecnica.back.end.client.RestCpfClient;
import avaliacao.tecnica.back.end.domain.Pauta;
import avaliacao.tecnica.back.end.domain.Votacao;
import avaliacao.tecnica.back.end.exception.AvaliacaoException;
import avaliacao.tecnica.back.end.producer.AvaliacaoProducer;
import avaliacao.tecnica.back.end.repository.PautaRepository;
import avaliacao.tecnica.back.end.repository.VotacaoRepository;
import avaliacao.tecnica.back.end.util.Constants;
import java.util.Optional;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceBackEndTest {

    @Mock
    private RestCpfClient client;
    @Mock
    private PautaRepository pauta;
    @Mock
    private VotacaoRepository votacao;
    @Mock
    private AvaliacaoProducer producer;
    @InjectMocks
    private ServiceBackEnd service;

    @Test
    public void testCadastrarPauta() {
        Pauta pauta = Constants.pauta();
        String expResult = Constants.criarPauta(pauta.getId());
        when(this.pauta.save(any())).thenReturn(pauta);
        String result = this.service.cadastrarPauta(null);

        assertEquals(expResult, result);
    }

    @Test
    public void testVotacaoEncerrada() {
        Pauta pauta = Constants.pauta();
        Votacao votacao = Constants.votacao(pauta);

        AvaliacaoException exception = assertThrows(AvaliacaoException.class,
                () -> this.service.votar(pauta.getId(), votacao.getCpf(), votacao.getVoto()),
                () -> "Erro de negocio esperado");
        assertEquals("VOTACAO ENCERRADA!", exception.getMessage());
    }

    @Test
    public void testVotarCpfInvalido() {
        Pauta pauta = Constants.pauta();
        Votacao votacao = Constants.votacao(pauta);
        when(this.pauta.findById(any())).thenReturn(Optional.of(pauta));

        AvaliacaoException exception = assertThrows(AvaliacaoException.class,
                () -> this.service.votar(pauta.getId(), votacao.getCpf(), votacao.getVoto()),
                () -> "Erro de negocio esperado");
        assertEquals("CPF INVALIDO!", exception.getMessage());
    }

    @Test
    public void testJaVotou() {
        Pauta pauta = Constants.pauta();
        Votacao votacao = Constants.votacao(pauta);
        when(this.pauta.findById(any())).thenReturn(Optional.of(pauta));
        when(this.client.getValidaCpf(any())).thenReturn(Constants.cpfvalido());
        when(this.votacao.findByCpfAndPauta(any(), any())).thenReturn(Optional.of(votacao));

        AvaliacaoException exception = assertThrows(AvaliacaoException.class,
                () -> this.service.votar(pauta.getId(), votacao.getCpf(), votacao.getVoto()),
                () -> "Erro de negocio esperado");
        assertEquals("USUARIO JA VOTOU!", exception.getMessage());

    }

    @Test
    public void testVotoOK() {
        Pauta pauta = Constants.pauta();
        Votacao votacao = Constants.votacao(pauta);
        when(this.pauta.findById(any())).thenReturn(Optional.of(pauta));
        when(this.client.getValidaCpf(any())).thenReturn(Constants.cpfvalido());
        when(this.votacao.save(any())).thenReturn(votacao);
        String result = this.service.votar(pauta.getId(), votacao.getCpf(), votacao.getVoto());

        assertEquals(votacao.toString(), result);
    }

}
