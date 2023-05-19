package ca.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OfferingNotFoundException extends Exception{
    public OfferingNotFoundException() {
        super();
    }
}
