package com.hermandadproject.gestionpersonajes.exception;

public class PersonajeNotFoundException extends RuntimeException {
    public PersonajeNotFoundException(String message) {
        super(message);
    }
}
