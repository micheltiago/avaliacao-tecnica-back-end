package avaliacao.tecnica.back.end.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "VOTACAO")
public class Votacao {

    @Id
    @Column(name = "CPF")
    private String cpf;

    @Column(name = "VOTO")
    private String voto;
    
    @ManyToOne    
    @JoinColumn(name = "ID_PAUTA")
    private Pauta pauta;
    

}
