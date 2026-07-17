package com.hermandadproject.gestionpersonajes.exception;

/**
 * Indica que se ha intentado crear un segundo perfil para el mismo personaje.
 */
public class PerfilPersonajeAlreadyExistsException extends RuntimeException {
    public PerfilPersonajeAlreadyExistsException(String message) {
        super(message);
    }
}
