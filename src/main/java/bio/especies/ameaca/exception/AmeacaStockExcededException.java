package bio.especies.ameaca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmeacaStockExcededException extends Exception {

    public AmeacaStockExcededException(Long id, int quantityToIncrement) {
        super(String.format("Ameaça with %s ID to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
    }
}
