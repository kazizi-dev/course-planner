package ca.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException() {
        super("Department not found!");
    }
}