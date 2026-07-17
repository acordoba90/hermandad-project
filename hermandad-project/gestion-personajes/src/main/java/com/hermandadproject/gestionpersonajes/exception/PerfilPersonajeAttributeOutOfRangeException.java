package com.hermandadproject.gestionpersonajes.exception;

/**
 * Indica que un atributo de perfil queda fuera de la escala permitida.
 */
public class PerfilPersonajeAttributeOutOfRangeException extends RuntimeException {
    public PerfilPersonajeAttributeOutOfRangeException(String message) {
        super(message);
    }
}
