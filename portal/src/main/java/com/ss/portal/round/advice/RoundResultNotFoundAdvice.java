package com.ss.portal.round.advice;

import com.ss.portal.round.exception.RoundResultNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoundResultNotFoundAdvice {

    @ExceptionHandler(RoundResultNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> handleRoundResultNotFound(
        RoundResultNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

}
