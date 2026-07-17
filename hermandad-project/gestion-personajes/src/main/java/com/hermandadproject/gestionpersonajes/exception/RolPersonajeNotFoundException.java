package com.hermandadproject.gestionpersonajes.exception;

/**
 * Excepcion funcional lanzada cuando un rol de personaje no existe o no esta activo.
 */
public class RolPersonajeNotFoundException extends RuntimeException {

    public RolPersonajeNotFoundException(String message) {
        super(message);
    }
}
