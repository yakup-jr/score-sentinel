package com.ss.portal.tournament.advice;

import com.ss.portal.tournament.exception.TournamentNofFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TournamentNofFoundAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> tournamentNotFoundException(
        TournamentNofFoundException ex) {
        return new ResponseEntity(ex, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
