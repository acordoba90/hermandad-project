package com.hermandadproject.gestionpersonajes.exception;

/**
 * Indica que un personaje no tiene perfil jugable asociado.
 */
public class PerfilPersonajeNotFoundException extends RuntimeException {
    public PerfilPersonajeNotFoundException(String message) {
        super(message);
    }
}
