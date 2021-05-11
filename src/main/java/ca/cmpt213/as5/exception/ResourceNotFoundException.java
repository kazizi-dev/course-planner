package ca.cmpt213.as5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    This class is used for exception handling to indicate
    if a subject, catalog, etc is empty or null.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String str){
        super(str);
    }
}