package com.ss.portal.game.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(Long id) {
        super("Game with id " + id + " not found");
    }

    public GameNotFoundException(String name) {
        super("Game with name " + name + " not found");
    }

    public GameNotFoundException(String name, Long id) {
        super("Game with id " + id + " and name " + name + " not found");
    }
}
