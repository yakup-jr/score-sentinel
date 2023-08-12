package com.ss.portal.round.exception;

public class RoundResultNotFoundException extends RuntimeException {
    public RoundResultNotFoundException(Long id) {
        super("Round result with id " + id + " not found");
    }

    public RoundResultNotFoundException(String message) {
        super(message);
    }
}
