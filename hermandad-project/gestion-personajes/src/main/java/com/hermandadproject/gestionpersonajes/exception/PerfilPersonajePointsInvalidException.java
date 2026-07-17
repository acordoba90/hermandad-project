package com.hermandadproject.gestionpersonajes.exception;

/**
 * Indica que el reparto de puntos de un perfil personalizado no cumple las reglas.
 */
public class PerfilPersonajePointsInvalidException extends RuntimeException {
    public PerfilPersonajePointsInvalidException(String message) {
        super(message);
    }
}
