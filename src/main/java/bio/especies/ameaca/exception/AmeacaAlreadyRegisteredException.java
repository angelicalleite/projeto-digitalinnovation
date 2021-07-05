package bio.especies.ameaca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmeacaAlreadyRegisteredException extends Exception{
    public AmeacaAlreadyRegisteredException(String ameacaName) {
        super(String.format("Ameaca with name %s already registered in the system.", ameacaName));
    }
}
