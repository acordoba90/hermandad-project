package com.hermandadproject.gestionciudad.exception;

public class CiudadNotFoundException extends RuntimeException {
    public CiudadNotFoundException(String message) {
        super(message);
    }
}
