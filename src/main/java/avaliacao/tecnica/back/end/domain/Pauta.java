package avaliacao.tecnica.back.end.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@Builder
@Entity
@Table(name = "PAUTA")
public class Pauta implements Serializable {

    @Id
    @Column(name = "ID_PAUTA")
    private String id;

    @Column(name = "TEMPO")
    private Integer tempo;

    @Column(name = "DATA_SESSAO")
    private LocalDateTime dataSessao;

}
