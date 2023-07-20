package com.ss.portal.team.advice;

import com.ss.portal.team.exception.TeamNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeamNotFoundAdvice {

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> handleTeamNotFound(TeamNotFoundException ex) {
        return new ResponseEntity(ex.getMessage(), new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }
}
