package avaliacao.tecnica.back.end.producer;

import avaliacao.tecnica.back.end.domain.Votacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AvaliacaoProducer {

    @Value("${producer.topic}")
    private String TOPIC;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    public void send(Votacao evento) {
        try {
            String message = mapper.writeValueAsString(evento);
            log.info("Producer :" + message);
            this.kafka.send(TOPIC, evento.getCpf(), message);
        } catch (Exception ex) {
            log.error("Producer: " + ex.getMessage());
        }
    }
}
