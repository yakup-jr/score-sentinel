package com.ss.portal.tournament.advice;

import com.ss.portal.tournament.exception.TournamentNotFoundException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TournamentNofFoundAdvice {

    protected Logger logger;

    @ExceptionHandler(TournamentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such tournament")
    public String tournamentNotFoundException(
        TournamentNotFoundException ex) {
        logger.error(ex.getClass().getSimpleName());

        return "no such value";
    }
}
