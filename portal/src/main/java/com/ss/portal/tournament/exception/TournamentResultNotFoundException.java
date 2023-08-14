package com.ss.portal.tournament.exception;

public class TournamentResultNotFoundException extends RuntimeException {

    public TournamentResultNotFoundException(Long id) {
        super("tournament result with id " + id + " not found");
    }

    public TournamentResultNotFoundException(String message) {
        super(message);
    }

}
