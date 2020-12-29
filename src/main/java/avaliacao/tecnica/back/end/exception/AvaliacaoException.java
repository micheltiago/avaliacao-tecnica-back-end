package avaliacao.tecnica.back.end.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AvaliacaoException extends RuntimeException {

    public AvaliacaoException(String message) {
        super(message);
    }
}
