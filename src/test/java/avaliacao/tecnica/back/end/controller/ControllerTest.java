package avaliacao.tecnica.back.end.controller;

import avaliacao.tecnica.back.end.service.MyService;
import avaliacao.tecnica.back.end.util.Constants;
import org.aspectj.apache.bcel.classfile.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AvaliacaoController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    MyService service;

    @Test
    public void testCadastrar() throws Exception {
        System.out.println("cadastrar");
        String tempo = "6";
        when(this.service.cadastrarPauta(null)).thenReturn(any());
        mvc.perform(MockMvcRequestBuilders
                .get("/pauta/{tempo}", tempo)
                .param("tempo", tempo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    public void testVotar() throws Exception {
        System.out.println("votar");
        String pauta = "384331500";
        String voto = "S";
        String cpf = "12345678909";
        when(this.service.cadastrarPauta(null)).thenReturn(Constants.criarPauta(pauta));
        mvc.perform(MockMvcRequestBuilders
                .get("/votacao/{pauta}/{voto}/{cpf}", pauta, voto, cpf)
                .param("pauta", pauta)
                .param("voto", voto)
                .param("cpf", cpf)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

    }

}
