package com.ss.portal.team.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long id) {
        super("Team with id " + id + " not found");
    }

    public TeamNotFoundException(String name) {
        super("Team with name " + name + " not found");
    }
}
