package com.ss.portal.match.exception;

public class MatchResultNotFoundException extends RuntimeException {

    public MatchResultNotFoundException(Long id) {
        super("Match result with id " + id + " not found");
    }

    MatchResultNotFoundException(String message) {
        super("Match result with " + message + " not found");
    }
}
