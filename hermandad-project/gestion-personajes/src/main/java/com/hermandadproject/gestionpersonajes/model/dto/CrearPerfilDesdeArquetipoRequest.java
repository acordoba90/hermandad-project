package com.hermandadproject.gestionpersonajes.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Peticion para crear el perfil de un personaje copiando los atributos base de un arquetipo.
 */
public record CrearPerfilDesdeArquetipoRequest(
        @NotNull UUID arquetipoPerfilId
) {
}
