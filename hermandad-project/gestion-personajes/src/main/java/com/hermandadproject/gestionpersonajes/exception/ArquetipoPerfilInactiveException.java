package com.hermandadproject.gestionpersonajes.exception;

/**
 * Indica que se ha intentado usar un arquetipo inactivo para crear un perfil.
 */
public class ArquetipoPerfilInactiveException extends RuntimeException {
    public ArquetipoPerfilInactiveException(String message) {
        super(message);
    }
}
