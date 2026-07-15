package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Peticion publica para confirmar una restauracion de contrasena con token.
 *
 * @param token token recibido en el enlace de restauracion.
 * @param nuevaContrasena nueva contrasena elegida por el usuario.
 * @param confirmacionContrasena repeticion defensiva de la nueva contrasena.
 */
public record ConfirmacionRestauracionContrasenaRequest(
        @NotBlank String token,
        @NotBlank @Size(max = 72) String nuevaContrasena,
        @NotBlank @Size(max = 72) String confirmacionContrasena
) {
}
