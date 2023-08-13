package com.ss.portal.round.advice;

import com.ss.portal.round.exception.RoundNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoundNotFoundAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> handleRoundNotFound(RoundNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }


}
