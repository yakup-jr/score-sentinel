package com.ss.portal.match.advice;

import com.ss.portal.match.exception.MatchNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MatchNotFoundAdviceException {

    @ExceptionHandler(MatchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> handleMatchNotFound(MatchNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

}
