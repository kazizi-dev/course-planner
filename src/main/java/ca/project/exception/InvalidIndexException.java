package ca.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/*
    This class is used for exception handling to indicate
    if a course list or a course of type CoursePlanner is
    empty or null.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIndexException extends Exception {
    public InvalidIndexException(String str){
        super(str);
    }
    public InvalidIndexException(){
        super();
    }
}


/*
    Note: Use the id of each class to set the text and
    explain that the course id does not exist.
 */