package com.ss.portal.tournament.exception;

public class TournamentNofFoundException extends RuntimeException {

    public TournamentNofFoundException(Long id) {
        super("tournament with id " + id + " not found");
    }

    public TournamentNofFoundException(String message) {
        super(message);
    }

}
