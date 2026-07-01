package com.hermandadproject.gestionpersonajes.exception;

public class PersonajeAlreadyExistsException extends RuntimeException {
    public PersonajeAlreadyExistsException(String message) {
        super(message);
    }
}
