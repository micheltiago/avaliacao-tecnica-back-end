package avaliacao.tecnica.back.end.dto;

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
public class RetornoDto {

    //ABLE_TO_VOTE
    //UNABLE_TO_VOTE
    private String status;
}
