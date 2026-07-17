package com.hermandadproject.gestionpersonajes.exception;

/**
 * Excepcion funcional lanzada cuando se intenta usar un colectivo inactivo en una operacion de negocio.
 */
public class ColectivoInactiveException extends RuntimeException {

    public ColectivoInactiveException(String message) {
        super(message);
    }
}
