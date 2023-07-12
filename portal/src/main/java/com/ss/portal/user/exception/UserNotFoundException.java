package com.ss.portal.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }

    public UserNotFoundException(String username, String email) {
            super(username == null ? "User with email " + email + " not found"
    : "User with username " + username + " not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
