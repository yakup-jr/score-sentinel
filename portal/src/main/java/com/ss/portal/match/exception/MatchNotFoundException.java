package com.ss.portal.match.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(Long id) {
        super("Match with id " + id + " not found");
    }

    public MatchNotFoundException(String message) {
        super(message);
    }
}
