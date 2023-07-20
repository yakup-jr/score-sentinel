package com.ss.portal.team.advice;

import com.ss.portal.team.exception.TeamAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeamAlreadyExistExceptionAdvice {

    @ExceptionHandler(TeamAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<String> teamAlreadyExistException(
        TeamAlreadyExistException exception) {
        return new ResponseEntity(exception, new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
