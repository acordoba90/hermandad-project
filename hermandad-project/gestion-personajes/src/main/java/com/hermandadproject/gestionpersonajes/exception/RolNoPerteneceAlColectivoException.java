package com.hermandadproject.gestionpersonajes.exception;

/**
 * Excepcion funcional lanzada cuando se intenta asignar a un personaje un rol de otro colectivo.
 */
public class RolNoPerteneceAlColectivoException extends RuntimeException {

    public RolNoPerteneceAlColectivoException(String message) {
        super(message);
    }
}
