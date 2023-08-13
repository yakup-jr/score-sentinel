package com.ss.portal.round.exception;

public class RoundNotFoundException extends RuntimeException {
    public RoundNotFoundException(Long id) {
        super("Round with id " + id + " not found");
    }

    public RoundNotFoundException(String message) {
        super(message);
    }
}
