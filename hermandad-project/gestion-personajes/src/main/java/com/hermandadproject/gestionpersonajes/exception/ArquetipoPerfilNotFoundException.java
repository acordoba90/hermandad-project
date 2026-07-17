package com.hermandadproject.gestionpersonajes.exception;

/**
 * Indica que no existe el arquetipo de perfil solicitado.
 */
public class ArquetipoPerfilNotFoundException extends RuntimeException {
    public ArquetipoPerfilNotFoundException(String message) {
        super(message);
    }
}
