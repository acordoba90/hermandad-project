package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Peticion publica para solicitar el envio de un enlace de restauracion de contrasena.
 *
 * @param correoElectronico correo de la cuenta que solicita la restauracion.
 */
public record SolicitudRestauracionContrasenaRequest(
        @NotBlank @Email @Size(max = 150) String correoElectronico
) {

    /**
     * Normaliza espacios exteriores antes de que el servicio realice la busqueda.
     */
    public SolicitudRestauracionContrasenaRequest {
        if (correoElectronico != null) {
            correoElectronico = correoElectronico.trim();
        }
    }
}
