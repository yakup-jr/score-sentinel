package com.ss.portal.tournament.exception;

public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(Long id) {
        super("tournament with id " + id + " not found");
    }

    public TournamentNotFoundException(String message) {
        super(message);
    }

}
