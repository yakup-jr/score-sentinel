package com.ss.portal.team.exception;

public class TeamAlreadyExistException extends RuntimeException {

    public TeamAlreadyExistException(String name) {
        super("User with name " + name + " already exists");
    }

    public TeamAlreadyExistException(Long id) {
        super("User with id " + id + " already exists");
    }
}
