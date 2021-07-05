package bio.especies.ameaca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AmeacaNotFoundException extends Exception {

    public AmeacaNotFoundException(Long id) {
        super(String.format("Ameaca com ID %s não foi encontrado!", id));
    }
    public AmeacaNotFoundException(String especie) {
        super(String.format("Ameaca com especie %s não foi encontrado!", especie));
    }
}
