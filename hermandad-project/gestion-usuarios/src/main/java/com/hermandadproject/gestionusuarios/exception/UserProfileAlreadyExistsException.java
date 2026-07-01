package com.hermandadproject.gestionusuarios.exception;

public class UserProfileAlreadyExistsException extends RuntimeException {
    public UserProfileAlreadyExistsException(String message) {
        super(message);
    }
}
